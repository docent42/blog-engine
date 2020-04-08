package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseRegisterDto {
    boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    HashMap<String, String> errors;
}
