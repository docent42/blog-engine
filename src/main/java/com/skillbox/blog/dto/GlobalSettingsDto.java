package com.skillbox.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalSettingsDto {

  @JsonProperty("MULTIUSER_MODE")
  boolean MULTIUSER_MODE;

  @JsonProperty("POST_PREMODERATION")
  boolean POST_PREMODERATION;

  @JsonProperty("STATISTICS_IS_PUBLIC")
  boolean STATISTICS_IS_PUBLIC;
}