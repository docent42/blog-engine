package com.skillbox.blog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class GlobalSettingsConfig {

    @Value("${globalsettings.multiuser}")
    boolean multiuserMode;

    @Value("${globalsettings.premoderpost}")
    boolean postPremoderation;

    @Value("${globalsettings.publicstats}")
    boolean statisticIsPublic;
}