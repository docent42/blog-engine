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
public class ResponseAllPostsDto {

  int count;
  List<PartInfoOfPosts> posts;
}
