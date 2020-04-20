package com.skillbox.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatisticsDto {

  int postsCount;
  int likesCount;
  int dislikesCount;
  int viewsCount;
  String firstPublication;
}
