package com.skillbox.blog.mapper;

import com.skillbox.blog.config.GlobalSettingsConfig;
import com.skillbox.blog.dto.GlobalSettingsDto;
import org.mapstruct.Mapper;

@Mapper
public interface GlobalSettingsConfigToDto {

    GlobalSettingsDto map(GlobalSettingsConfig globalSettingsConfig);
}