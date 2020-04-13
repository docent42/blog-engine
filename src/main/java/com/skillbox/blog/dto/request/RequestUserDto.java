package com.skillbox.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RequestUserDto {

  @Email
  @JsonProperty("e_mail")
  private String email;
  @Size(min = 6)
  private String password;
  @Size(min = 6)
  private String captcha;
}
