package com.skillbox.blog.service;

import com.skillbox.blog.config.GlobalSettingsConfig;
import com.skillbox.blog.dto.GlobalSettingsDto;
import com.skillbox.blog.exception.StatusException;
import com.skillbox.blog.mapper.GlobalSettingsConfigToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private GlobalSettingsConfig globalSettingsConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalSettingsConfigToDto mapper;

    public GlobalSettingsDto getSettings() {
        if (userService.getCurrentUser().getIsModerator() == 1) {
            return mapper.map(globalSettingsConfig);
        }
        throw new StatusException("But it is for You.");
    }

    public GlobalSettingsDto saveSettings(GlobalSettingsDto request) {
        if (userService.getCurrentUser().getIsModerator() == 1) {
            globalSettingsConfig.setMultiuserMode(request.isMultiuserMode());
            globalSettingsConfig.setPostPremoderation(request.isPostPremoderation());
            globalSettingsConfig.setStatisticIsPublic(request.isStatisticIsPublic());
        } else {
            throw new StatusException("But it is for You.");
        }
        return request;
    }
}