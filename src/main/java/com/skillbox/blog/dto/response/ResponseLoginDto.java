package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseLoginDto<T extends Object> extends ResponseResults<Object> {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  ResponseUserDto user;
}




