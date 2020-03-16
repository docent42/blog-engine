package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestPost;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.dto.response.errors.ErrorTitleText;
import com.skillbox.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService service) {
        this.postService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseResults<ErrorTitleText> createPost(@Valid @RequestBody RequestPost requestPost) {

        return postService.createPost(requestPost);
    }


}
