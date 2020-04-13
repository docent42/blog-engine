package com.skillbox.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {

  private int id;
  private String name;
  private String photo;
  private String email;
  private Boolean moderation;
  private Integer moderationCount;
  private Boolean settings;
}
