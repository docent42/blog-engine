package com.skillbox.blog.service;

import com.skillbox.blog.dto.response.ResponsePostDto;
import com.skillbox.blog.entity.Post;
import com.skillbox.blog.entity.enums.ModerationStatus;
import com.skillbox.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModerationService {

  @Autowired
  PostRepository postRepository;

  public ResponsePostDto moderationPost(int postId, String decision) {
    Post post = postRepository.findById(postId).get();

    ModerationStatus moderationStatus = ModerationStatus.valueOf(decision.toUpperCase());
    post.setModerationStatus(moderationStatus);

    postRepository.save(post);
    return new ResponsePostDto(true);
  }
}
