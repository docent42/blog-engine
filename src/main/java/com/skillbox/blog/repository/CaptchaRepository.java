package com.skillbox.blog.repository;

import com.skillbox.blog.entity.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CaptchaRepository extends JpaRepository<CaptchaCode, Long> {

  @Query("SELECT c.secretCode FROM CaptchaCode c WHERE c.code = :code")
  String findCaptchaCodeByCode(@Param("code") String code);
}
