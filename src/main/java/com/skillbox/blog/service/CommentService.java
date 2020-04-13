package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestCommentDto;
import com.skillbox.blog.dto.request.RequestPost;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.entity.Post;
import com.skillbox.blog.entity.PostComment;
import com.skillbox.blog.mapper.RequestPostToPost;
import com.skillbox.blog.repository.PostCommentRepository;
import com.skillbox.blog.repository.PostRepository;
import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

  PostRepository postRepository;
  PostService postService;
  UserService userService;
  PostCommentRepository postCommentRepository;
  RequestPostToPost requestMapper;

  public ResponseResults<Integer> createComment(RequestCommentDto comment) {
    PostComment commentToSave = new PostComment();
    Post post = postRepository.findById(comment.getPostId())
        .orElseThrow(EntityNotFoundException::new);
    commentToSave.setPostId(post);
    commentToSave.setTime(LocalDateTime.now());
    commentToSave.setText(comment.getText());
    commentToSave.setUserId(userService.getCurrentUser());

    if (comment.getParentId() != 0) {
      PostComment parent = postCommentRepository.findById(comment.getParentId())
          .orElseThrow(EntityNotFoundException::new);
      commentToSave.setParentId(parent);
    }
    return new ResponseResults<Integer>().setId(postCommentRepository.save(commentToSave).getId());
  }

  public ResponseResults<Boolean> editComment(RequestPost editPost, int postId) {
    Post oldPost = postService.getPostById(postId);
    Post postToSave = requestMapper.mapEdit(editPost);
    postToSave.setUserId(oldPost.getUserId());
    postToSave.setModeratorId(oldPost.getModeratorId());

    if (userService.isModerator()) {
      postToSave.setModerationStatus(oldPost.getModerationStatus());
    }
    postToSave.setTagList(postService.updateTags(editPost.getTags()));

    postRepository.save(postToSave);
    return new ResponseResults<Boolean>().setResult(true);
  }
}
