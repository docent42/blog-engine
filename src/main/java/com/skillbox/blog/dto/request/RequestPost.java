package com.skillbox.blog.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RequestPost {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime time;
  private byte active;
  @Size(max = 256, message = "Заголовок не установлен")
  private String title;
  @Size(min = 50, message = "Текст публикации слишком короткий")
  private String text;
  private String tags;
}
