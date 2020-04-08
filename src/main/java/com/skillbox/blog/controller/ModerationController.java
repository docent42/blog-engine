package com.skillbox.blog.controller;

import com.skillbox.blog.dto.response.ResponsePostDto;
import com.skillbox.blog.service.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ModerationController {

  @Autowired
  private ModerationService moderationService;

  @PostMapping("/moderation")
  @ResponseStatus(HttpStatus.OK)
  public ResponsePostDto moderationPost(@RequestParam int postId,
                                        @RequestParam String decision) {
    return moderationService.moderationPost(postId, decision);
  }
}
