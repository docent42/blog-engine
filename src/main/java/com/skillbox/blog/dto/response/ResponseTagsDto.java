package com.skillbox.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseTagsDto {
    List<TagDto> tags;
}
