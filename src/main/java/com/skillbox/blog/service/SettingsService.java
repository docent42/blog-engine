package com.skillbox.blog.service;

import com.skillbox.blog.dto.GlobalSettingsDto;
import com.skillbox.blog.entity.GlobalSetting;
import com.skillbox.blog.exception.StatusException;
import com.skillbox.blog.mapper.GlobalSettingsConfigToDto;
import com.skillbox.blog.repository.GlobalSettingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

  @Autowired
  private GlobalSettingRepository repository;

  @Autowired
  private UserService userService;

  @Autowired
  private GlobalSettingsConfigToDto mapper;

  public GlobalSettingsDto getSettings() {
    if (userService.getCurrentUser().getIsModerator() == 1) {
      return mapper.map(repository.findAll());
    }
    throw new StatusException("But it is for You.");
  }

  public GlobalSettingsDto saveSettings(GlobalSettingsDto dto) {
    if (userService.getCurrentUser().getIsModerator() == 1) {
      List<GlobalSetting> settings = repository.findAll();
      repository.saveAll(mapper.map(dto, settings));
    } else {
      throw new StatusException("But it is for You.");
    }
    return dto;
  }
}