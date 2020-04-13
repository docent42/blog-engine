package com.skillbox.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseTagsDto {

  List<TagDto> tags;
}
