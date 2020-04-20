package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCalendarDto {

  @JsonProperty("years")
  List<Integer> years;

  @JsonProperty("posts")
  Map<String, Integer> posts;
}
