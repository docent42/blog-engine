package com.skillbox.blog.dto.response;

import lombok.Data;

@Data
public class ResponseCaptchaDto {

  private String secret;
  private String image;
}
