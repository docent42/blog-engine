package com.skillbox.blog.dto.response.temporary;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemporaryComment {

  int commentId;
  LocalDateTime time;
  int userId;
  String name;
  String photo;
  String text;
}
