package com.skillbox.blog.mapper;

import com.skillbox.blog.dto.response.ResponseUserDto;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserToResponseDtoDecorator implements UserToResponseLoginDto {

  @Autowired
  @Qualifier("delegate")
  private UserToResponseLoginDto delegate;

  @Autowired
  private PostRepository postRepository;

  @Override
  public ResponseUserDto map(User user) {
    ResponseUserDto dto = delegate.map(user);
    dto.setModerationCount(postRepository.findCountPostsForModeration(user.getId()));
    return dto;
  }
}
