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
    boolean multiuserMode;

    @JsonProperty("POST_PREMODERATION")
    boolean postPremoderation;

    @JsonProperty("STATISTIC_IS_PUBLIC")
    boolean statisticIsPublic;
}