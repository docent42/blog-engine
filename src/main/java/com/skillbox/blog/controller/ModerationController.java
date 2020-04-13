package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestModerationDto;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.service.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ModerationController {

  @Autowired
  private ModerationService moderationService;

  @PostMapping("/moderation")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults<Boolean> moderationPost(
      @RequestBody RequestModerationDto requestModerationDto) {
    return moderationService.moderationPost(requestModerationDto);
  }
}
