package com.skillbox.blog.mapper;

import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.entity.CaptchaCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CaptchaToCaptchaDto {

    @Mapping(source = "code", target = "secret")
    @Mapping(source = "secretCode", target = "image")
    ResponseCaptchaDto map(CaptchaCode captchaCode);
}
