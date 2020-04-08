package com.skillbox.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOnePostDto {

  int id;
  String time;
  PartInfoOfUser user;
  String title;
  String text;
  int likeCount;
  int dislikeCount;
  int viewCount;
  List<PartComment> comments;
  String[] tags;
}
