package com.skillbox.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestEditProfileDto {

  private Byte removePhoto;
  private String name;
  private String email;
  private String password;
}