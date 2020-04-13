package com.skillbox.blog.repository;

import com.skillbox.blog.entity.GlobalSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingRepository extends JpaRepository<GlobalSetting, Integer> {

}
