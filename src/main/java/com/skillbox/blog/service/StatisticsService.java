package com.skillbox.blog.service;

import com.skillbox.blog.config.GlobalSettingsConfig;
import com.skillbox.blog.dto.response.ResponseStatisticsDto;
import com.skillbox.blog.repository.PostRepository;
import com.skillbox.blog.repository.PostVoteRepository;
import java.nio.file.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StatisticsService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostVoteRepository postVoteRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private GlobalSettingsConfig globalSettingsConfig;

  public ResponseStatisticsDto getStatisticsForCurrentUser() {
    int userId = userService.getCurrentUser().getId();
    return new ResponseStatisticsDto().builder()
        .posts(postRepository.findCountPostsForCurrentUser(userId))
        .likes(postVoteRepository.findCountOfLikesForCurrentUser(userId))
        .dislikes(postVoteRepository.findCountOfDislikesForCurrentUser(userId))
        .views(postRepository.findCountViewsOfPostsForCurrentUser(userId))
        .firstPublication(postRepository.findFirstPublicationForCurrentUser(userId))
        .build();
  }

  public ResponseStatisticsDto getStatisticForAll() throws AccessDeniedException {
    if (globalSettingsConfig.isStatisticIsPublic()) {
      return new ResponseStatisticsDto().builder()
          .posts(postRepository.findCountPosts())
          .likes(postVoteRepository.findCountOfAllLikes())
          .dislikes(postVoteRepository.findCountOfAllDislikes())
          .views(postRepository.findCountAllViews())
          .firstPublication(postRepository.findFirstPublication())
          .build();
    } else {
      throw new AccessDeniedException("Statistics hidden by moderator!");
    }
  }
}