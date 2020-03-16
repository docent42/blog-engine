package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Builder
@Data
public class ResponseLoginDto {
    boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ResponseUserDto user;
}




