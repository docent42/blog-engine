package com.skillbox.blog.dto.request;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RequestCommentDto {

  private int parentId;

  private int postId;

  @Size(max = 200, message = "Текст публикации слишком короткий")
  private String text;
}
