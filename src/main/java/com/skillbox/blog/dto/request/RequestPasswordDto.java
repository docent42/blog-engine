package com.skillbox.blog.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class RequestPasswordDto {

  String code;
  String password;
  String captcha;
  String captcha_secret;
}
