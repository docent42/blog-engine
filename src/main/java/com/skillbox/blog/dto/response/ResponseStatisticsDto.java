package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatisticsDto {

  @JsonProperty("Постов")
  int posts;

  @JsonProperty("Лайков")
  int likes;

  @JsonProperty("Дизлайков")
  int dislikes;

  @JsonProperty("Просмотров")
  int views;

  @JsonProperty("Первая публикация")
  LocalDateTime firstPublication;
}