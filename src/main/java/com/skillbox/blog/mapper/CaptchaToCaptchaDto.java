package com.skillbox.blog.mapper;

import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.entity.CaptchaCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CaptchaToCaptchaDto {

  @Mapping(source = "secretCode", target = "secret")
  ResponseCaptchaDto map(CaptchaCode captchaCode);
}
