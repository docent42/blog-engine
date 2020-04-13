package com.skillbox.blog.mapper;

import com.skillbox.blog.dto.request.RequestUserDto;
import com.skillbox.blog.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoToUser {

  User map(RequestUserDto requestUserDto);
}
