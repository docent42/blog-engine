package com.skillbox.blog.mapper;

import com.skillbox.blog.config.GlobalSettingsConfig;
import com.skillbox.blog.dto.GlobalSettingsDto;
import com.skillbox.blog.entity.GlobalSetting;
import java.lang.reflect.Field;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface GlobalSettingsConfigToDto {

  GlobalSetting map(GlobalSettingsConfig.GlobalSettingConfig config);

  @InheritInverseConfiguration
  default GlobalSettingsDto map(List<GlobalSetting> list) {
    GlobalSettingsDto dto = new GlobalSettingsDto();
    Field[] dtoFields = dto.getClass().getDeclaredFields();

    try {
      for (int i = 0; i < dtoFields.length; i++) {
        dtoFields[i].setAccessible(true);
        int finalI = i;
        boolean fieldVal = list.stream()
            .filter(
                gs -> gs.getCode().equals(dtoFields[finalI].getName()))
            .findFirst().orElseThrow()
            .getValue().toLowerCase().equals("yes");

        dtoFields[i].set(
            dto,
            fieldVal
        );
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return dto;
  }

  default List<GlobalSetting> map(GlobalSettingsDto dto, List<GlobalSetting> settings) {
    Field[] dtoFields = dto.getClass().getDeclaredFields();

    try {
      for (int i = 0; i < dtoFields.length; i++) {
        dtoFields[i].setAccessible(true);
        int finalI = i;
        settings.stream()
            .filter(
                gs -> gs.getCode().equals(dtoFields[finalI].getName())
            )
            .findFirst().orElseThrow()
            .setValue(((Boolean) dtoFields[i].get(dto) ? "yes" : "no"));
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return settings;
  }
}