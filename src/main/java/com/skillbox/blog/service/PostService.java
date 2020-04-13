package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestLikeDislikeDto;
import com.skillbox.blog.dto.request.RequestPost;
import com.skillbox.blog.dto.response.PartComment;
import com.skillbox.blog.dto.response.PartInfoOfPosts;
import com.skillbox.blog.dto.response.PartInfoOfUser;
import com.skillbox.blog.dto.response.ResponseAllPostsDto;
import com.skillbox.blog.dto.response.ResponseOnePostDto;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.dto.response.temporary.TemporaryComment;
import com.skillbox.blog.entity.Post;
import com.skillbox.blog.entity.PostVoteEntity;
import com.skillbox.blog.entity.Tag;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.exception.IllegalValueException;
import com.skillbox.blog.mapper.RequestPostToPost;
import com.skillbox.blog.repository.PostCommentRepository;
import com.skillbox.blog.repository.PostRepository;
import com.skillbox.blog.repository.PostVoteRepository;
import com.skillbox.blog.repository.TagRepository;
import com.skillbox.blog.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

  PostRepository postRepository;
  TagRepository tagRepository;
  UserRepository userRepository;
  RequestPostToPost requestMapper;
  UserService userService;
  PostVoteRepository postVoteRepository;
  PostCommentRepository postCommentRepository;

  public ResponseAllPostsDto getPosts(int offset, int limit, String mode) {
    int count = postRepository.findCountPosts();
    PostService.SORT sortMode = PostService.SORT.valueOf(mode.toUpperCase());

    Pageable pageable = PageRequest.of(offset / limit, limit);
    List<Post> posts = postRepository.findSuitablePosts(pageable);
    List<PartInfoOfPosts> result = postConversion(posts);
    sortList(result, sortMode);

    posts.forEach(p -> p.setViewCount(p.getViewCount() + 1));
    return new ResponseAllPostsDto().builder()
        .count(count)
        .posts(result)
        .build();
  }

  public ResponseAllPostsDto searchPosts(int offset, int limit, String query) {
    if (query.equals("")) {
      return getPosts(offset, limit, "best");
    } else {
      int count = postRepository.findCountPosts();

      if (count == 0) {
        throw new EntityNotFoundException("Post / Comment not exist ");
      }

      Pageable pageable = PageRequest.of(offset / limit, limit);
      List<Post> posts = postRepository.findAllPostsByQuery(query, pageable);

      if (posts.isEmpty()) {
        throw new EntityNotFoundException("Post / Comment not exist ");
      }

      posts.forEach(p -> p.setViewCount(p.getViewCount() + 1));
      return ResponseAllPostsDto.builder()
          .count(count)
          .posts(postConversion(posts))
          .build();
    }
  }

  public ResponseOnePostDto getPost(int postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("Post not found !"));

    User user = userRepository.findById(post.getUserId().getId());
    post.setViewCount(post.getViewCount() + 1);

    PartInfoOfUser partInfoOfUser = PartInfoOfUser.builder()
        .id(user.getId())
        .name(user.getName())
        .build();

    List<TemporaryComment> temporaryComments = postCommentRepository.findListByPostId(post);
    List<PartComment> comments = new ArrayList<>();

    for (TemporaryComment temporaryComment : temporaryComments) {

      PartInfoOfUser partInfoOfUserTemporary = PartInfoOfUser.builder()
          .id(temporaryComment.getUserId())
          .name(temporaryComment.getName())
          .photo(temporaryComment.getPhoto())
          .build();

      PartComment partComment = PartComment.builder()
          .id(temporaryComment.getCommentId())
          .time(dateMapping(temporaryComment.getTime()))
          .user(partInfoOfUserTemporary)
          .text(temporaryComment.getText())
          .build();

      comments.add(partComment);
    }

    String[] tags = tagRepository.findByPostId(postId);

    return new ResponseOnePostDto().builder()
        .id(post.getId())
        .time(dateMapping(post.getTime()))
        .user(partInfoOfUser)
        .title(post.getTitle())
        .text(post.getText())
        .likeCount(postVoteRepository.findCountOfLikesById(postId))
        .dislikeCount(postVoteRepository.findCountOfDislikesById(postId))
        .viewCount(postRepository.findViewCountByPostId(postId))
        .comments(comments)
        .tags(tags)
        .build();
  }

  public ResponseAllPostsDto getPostsByDate(int offset, int limit, String date) {
    int count = postRepository.findCountPosts();

    Pageable pageable = PageRequest.of(offset / limit, limit);
    List<Post> posts = postRepository.findByDate(date, pageable);
    List<PartInfoOfPosts> result = postConversion(posts);
    posts.forEach(p -> p.setViewCount(p.getViewCount() + 1));

    return ResponseAllPostsDto.builder()
        .count(count)
        .posts(result)
        .build();
  }

  public ResponseAllPostsDto getPostsByTag(int offset, int limit, String tag) {
    int count = postRepository.findCountPosts();

    Pageable pageable = PageRequest.of(offset / limit, limit);
    List<Post> posts = postRepository.findAllByTag(tag, pageable);
    List<PartInfoOfPosts> result = postConversion(posts);
    posts.forEach(p -> p.setViewCount(p.getViewCount() + 1));

    return ResponseAllPostsDto.builder()
        .count(count)
        .posts(result)
        .build();
  }

  public ResponseAllPostsDto getModerationList(String status) {
    int moderatorId = userService.getCurrentUser().getId();
    int count = postRepository
        .findCountPostsForModerationByStatus(moderatorId, status.toUpperCase());

    List<Post> postsForModeration = postRepository
        .findPostsForModerationByStatus(moderatorId, status.toUpperCase());

    List<PartInfoOfPosts> posts = new ArrayList<>();

    for (Post post : postsForModeration) {
      int userId = post.getUserId().getId();
      String userName = userRepository.findNameById(userId);
      String announce;

      if (post.getText().contains(".")) {
        announce = post.getText().substring(0, post.getText().indexOf(".") + 1);
      } else {
        announce = post.getText();
      }

      PartInfoOfUser partInfoOfUser = PartInfoOfUser.builder()
          .id(userId)
          .name(userName)
          .build();

      PartInfoOfPosts partInfoOfPosts = PartInfoOfPosts.builder()
          .id(post.getId())
          .time(dateMapping(post.getTime()))
          .user(partInfoOfUser)
          .title(post.getTitle())
          .announce(announce)
          .build();

      posts.add(partInfoOfPosts);
    }

    posts.forEach(p -> p.setViewCount(p.getViewCount() + 1));
    return ResponseAllPostsDto.builder()
        .count(count)
        .posts(posts)
        .build();
  }

  public ResponseAllPostsDto getMyPosts(int offset, int limit, String status) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    String moderationStatus = "%";
    int isActive = 0;
    int userId = userService.getCurrentUser().getId();
    if (!status.equals("inactive")) {
      isActive = 1;
    }

    switch (status) {
      case ("pending"): {
        moderationStatus = "NEW";
        break;
      }
      case ("declined"): {
        moderationStatus = "DECLINED";
        break;
      }
      case ("published"): {
        moderationStatus = "ACCEPTED";
      }
    }

    List<Post> myPosts = postRepository.findMyPosts(userId, isActive, moderationStatus, pageable);
    myPosts.forEach(p -> p.setViewCount(p.getViewCount() + 1));
    return ResponseAllPostsDto.builder()
        .count(myPosts.size())
        .posts(postConversion(myPosts))
        .build();
  }

  public ResponseResults<Boolean> createPost(RequestPost post) {
    Post postToSave = requestMapper.mapNew(post);
    postToSave.setUserId(userService.getCurrentUser());
    postToSave.setModeratorId(userService.getModerator());
    postToSave.setTagList(updateTags(post.getTags()));

    postRepository.save(postToSave);
    return new ResponseResults<Boolean>().setResult(true);
  }

  public ResponseResults<Boolean> like(RequestLikeDislikeDto requestLikeDislikeDto) {
    if (postRepository.findById(requestLikeDislikeDto.getPostId()).isPresent()) {
      User currentUser = userService.getCurrentUser();
      Optional<Integer> result = postVoteRepository
          .findValueByPostIdAndUserId(requestLikeDislikeDto.getPostId(), currentUser.getId());

      if (result.isEmpty()) {
        createAndSaveLike(currentUser, requestLikeDislikeDto);
        return new ResponseResults<Boolean>().setResult(true);

      } else if (result.get() == -1) {
        deleteDislike(currentUser, requestLikeDislikeDto);
        createAndSaveLike(currentUser, requestLikeDislikeDto);
        return new ResponseResults<Boolean>().setResult(true);

      } else if (result.get() == 1) {
        return new ResponseResults<Boolean>().setResult(false);
      } else {
        throw new IllegalValueException("Only 1 or -1.");
      }
    } else {
      throw new EntityNotFoundException("Post / Comment not exist ");
    }
  }

  public ResponseResults<Boolean> dislike(RequestLikeDislikeDto requestLikeDislikeDto) {
    if (postRepository.findById(requestLikeDislikeDto.getPostId()).isPresent()) {
      User currentUser = userService.getCurrentUser();
      Optional<Integer> result = postVoteRepository
          .findValueByPostIdAndUserId(requestLikeDislikeDto.getPostId(), currentUser.getId());

      if (result.isEmpty()) {
        createAndSaveDislike(currentUser, requestLikeDislikeDto);
        return new ResponseResults<Boolean>().setResult(true);

      } else if (result.get() == 1) {
        deleteLike(currentUser, requestLikeDislikeDto);
        createAndSaveDislike(currentUser, requestLikeDislikeDto);
        return new ResponseResults<Boolean>().setResult(true);

      } else if (result.get() == -1) {
        return new ResponseResults<Boolean>().setResult(false);
      } else {
        throw new IllegalValueException("Only 1 or -1.");
      }
    } else {
      throw new EntityNotFoundException("Post / Comment not exist ");
    }
  }

  List<Tag> updateTags(String tagsStr) {
    List<String> tags = Arrays.asList(tagsStr.trim()
        .toLowerCase()
        .split(","));

    List<Tag> existTagList = tagRepository.findAllByNameIn(tags);

    List<String> existTagListNames = existTagList.stream()
        .map(Tag::getName)
        .collect(Collectors.toList());

    List<Tag> newTagList = tags.stream().filter(tag -> !existTagListNames.contains(tag))
        .map(Tag::new)
        .collect(Collectors.toList());

    if (!newTagList.isEmpty()) {

      newTagList = tagRepository.saveAll(newTagList);
      existTagList.addAll(newTagList);
    }
    return existTagList;
  }

  Post getPostById(int postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("Post not found !"));
  }

  private void deleteDislike(User currentUser, RequestLikeDislikeDto requestLikeDislikeDto) {
    PostVoteEntity postDislike = postVoteRepository
        .findPostVoteByPostIdAndUserId(requestLikeDislikeDto.getPostId(), currentUser.getId());
    postVoteRepository.delete(postDislike);
  }

  private void createAndSaveLike(User currentUser, RequestLikeDislikeDto requestLikeDislikeDto) {
    PostVoteEntity newLike = new PostVoteEntity().builder()
        .postId(postRepository.findById(requestLikeDislikeDto.getPostId()).get())
        .time(LocalDateTime.now())
        .userId(currentUser)
        .value((byte) 1)
        .build();

    postVoteRepository.save(newLike);
  }

  private void deleteLike(User currentUser, RequestLikeDislikeDto requestLikeDislikeDto) {
    PostVoteEntity postLike = postVoteRepository
        .findPostVoteByPostIdAndUserId(requestLikeDislikeDto.getPostId(), currentUser.getId());
    postVoteRepository.delete(postLike);
  }

  private void createAndSaveDislike(User currentUser, RequestLikeDislikeDto requestLikeDislikeDto) {
    PostVoteEntity newDislike = new PostVoteEntity().builder()
        .postId(postRepository.findById(requestLikeDislikeDto.getPostId()).get())
        .time(LocalDateTime.now())
        .userId(currentUser)
        .value((byte) -1)
        .build();

    postVoteRepository.save(newDislike);
  }

  private enum SORT {
    RECENT,
    POPULAR,
    BEST,
    EARLY
  }

  private void sortList(List<PartInfoOfPosts> list, SORT sortMode) {
    if (sortMode == SORT.RECENT) {
      list.sort(Comparator.comparing(PartInfoOfPosts::getTime).reversed());

    } else if (sortMode == SORT.POPULAR) {
      list.sort(Comparator.comparing(PartInfoOfPosts::getCommentCount).reversed());

    } else if (sortMode == SORT.BEST) {
      list.sort(Comparator.comparing(PartInfoOfPosts::getLikeCount).reversed());

    } else if (sortMode == SORT.EARLY) {
      list.sort(Comparator.comparing(PartInfoOfPosts::getTime));
    }
  }

  private String dateMapping(LocalDateTime date) {
    DateTimeFormatter standardFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    DateTimeFormatter formattingToday = DateTimeFormatter.ofPattern("Сегодня, HH:mm");
    DateTimeFormatter formattingYesterday = DateTimeFormatter.ofPattern("Вчера, HH:mm");

    LocalDateTime today = LocalDateTime.now();
    final long oneDay = 1440;
    final long twoDays = 2880;

    if (Duration.between(date, today).toMinutes() <= oneDay) {
      return date.format(formattingToday);

    } else if (Duration.between(date, today).toMinutes() <= twoDays) {
      return date.format(formattingYesterday);

    } else {
      return date.format(standardFormatter);
    }
  }

  private List<PartInfoOfPosts> postConversion(List<Post> posts) {
    List<PartInfoOfPosts> result = new ArrayList<>();

    for (Post post : posts) {
      int userId = post.getUserId().getId();
      String userName = userRepository.findNameById(userId);
      int postId = post.getId();
      String announce;

      if (post.getText().contains(".")) {
        announce = post.getText().substring(0, post.getText().indexOf(".") + 1);
      } else {
        announce = post.getText();
      }

      PartInfoOfUser infoOfUser = PartInfoOfUser.builder()
          .id(userId)
          .name(userName)
          .build();

      PartInfoOfPosts infoOfPosts = PartInfoOfPosts.builder()
          .id(post.getId())
          .time(dateMapping(post.getTime()))
          .user(infoOfUser)
          .title(post.getTitle())
          .announce(announce)
          .likeCount(postVoteRepository.findCountOfLikesById(postId))
          .dislikeCount(postVoteRepository.findCountOfDislikesById(postId))
          .commentCount(postCommentRepository.findCountOfCommentsByPostId(postId))
          .viewCount(post.getViewCount())
          .build();

      result.add(infoOfPosts);
    }
    return result;
  }
}