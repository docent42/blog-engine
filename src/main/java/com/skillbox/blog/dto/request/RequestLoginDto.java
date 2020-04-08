package com.skillbox.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestLoginDto {

    @JsonProperty("e_mail")
    String email;
    String password;
}
