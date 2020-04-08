package com.skillbox.blog.repository;

import com.skillbox.blog.entity.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptchaRepository extends JpaRepository<CaptchaCode, Long> {
    String findCaptchaCodeByCode(String code);
}
