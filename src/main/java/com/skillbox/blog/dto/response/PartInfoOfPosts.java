package com.skillbox.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartInfoOfPosts {

  int id;
  String time;
  PartInfoOfUser user;
  String title;
  String announce;
  int likeCount;
  int dislikeCount;
  int commentCount;
  int viewCount;
}