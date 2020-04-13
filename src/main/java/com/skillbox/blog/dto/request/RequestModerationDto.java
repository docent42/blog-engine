package com.skillbox.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestModerationDto {

  @JsonProperty(value = "post_id")
  int postId;

  String decision;
}
