package com.skillbox.blog.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseCaptchaDto {

  private String secret;
  private String image;
}
