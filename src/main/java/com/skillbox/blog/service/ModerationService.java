package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestModerationDto;
import com.skillbox.blog.dto.response.ResponseResults;
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

  public ResponseResults<Boolean> moderationPost(RequestModerationDto requestModerationDto) {
    Post post = postRepository.findById(requestModerationDto.getPostId()).get();
    if (requestModerationDto.getDecision().toUpperCase().equals("ACCEPT")) {
      requestModerationDto.setDecision("ACCEPTED");
    }
    ModerationStatus moderationStatus = ModerationStatus
        .valueOf(requestModerationDto.getDecision().toUpperCase());
    System.out.println("================ " + moderationStatus);
    post.setModerationStatus(moderationStatus);

    postRepository.save(post);
    return new ResponseResults<Boolean>().setResult(true);
  }
}
