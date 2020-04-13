package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseCalendarDto {

  @JsonProperty("years")
  private Object[] years;

  @JsonProperty("posts")
  private TreeMap<String, Integer> posts;
}