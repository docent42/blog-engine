package com.skillbox.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RequestUserDto {

    @Email
    private String email;
    @NotBlank(message = "name must be not null")
    private String name;
    @Size(min = 6)
    private String password;
    @Size(min = 6)
    private String captcha;
}
