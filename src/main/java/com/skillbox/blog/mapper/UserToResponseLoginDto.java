package com.skillbox.blog.mapper;

import com.skillbox.blog.dto.response.ResponseUserDto;
import com.skillbox.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserToResponseLoginDto {

    @Mapping(target = "moderation", expression = "java(user.getIsModerator() != 0 ? true : false)")
    ResponseUserDto map(User user);
}