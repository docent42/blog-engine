package com.skillbox.blog.mapper;

import com.skillbox.blog.dto.response.ResponseUserDto;
import com.skillbox.blog.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserToResponseLoginDto {

    ResponseUserDto map(User user);
}
