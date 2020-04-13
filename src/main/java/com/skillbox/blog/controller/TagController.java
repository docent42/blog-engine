package com.skillbox.blog.controller;

import com.skillbox.blog.dto.response.ResponseTagsDto;
import com.skillbox.blog.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TagController {

  TagService tagService;

  @GetMapping("/tag")
  @ResponseStatus(HttpStatus.OK)
  public ResponseTagsDto getTags(@RequestParam(value = "query", defaultValue = "") String query) {
    return tagService.getTags(query);
  }
}
