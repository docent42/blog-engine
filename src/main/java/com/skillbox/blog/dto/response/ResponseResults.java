package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResults<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T errors;
}
