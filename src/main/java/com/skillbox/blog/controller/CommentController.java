package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestCommentDto;
import com.skillbox.blog.dto.request.RequestPost;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.service.CommentService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class CommentController {

  CommentService commentService;

  @PostMapping("/comment")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults<Integer> createComment(@Valid @RequestBody RequestCommentDto comment) {
    return commentService.createComment(comment);
  }
}
