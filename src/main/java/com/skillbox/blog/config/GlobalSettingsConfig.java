package com.skillbox.blog.config;

import com.skillbox.blog.mapper.GlobalSettingsConfigToDto;
import com.skillbox.blog.repository.GlobalSettingRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "global-settings")
public class GlobalSettingsConfig implements CommandLineRunner {

  @Setter
  List<GlobalSettingConfig> globalSettings;

  @Autowired
  GlobalSettingsConfigToDto mapper;
  @Autowired
  GlobalSettingRepository repository;

  @Override
  public void run(String... args) throws Exception {
    repository.deleteAll();
    repository.saveAll(
        globalSettings.stream()
            .map(gsc -> mapper.map(gsc))
            .collect(Collectors.toSet())
    );
  }

  @Getter
  @Setter
  public static class GlobalSettingConfig {

    private String code;
    private String name;
    private String value;
  }

}