package com.skillbox.blog.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  MODERATOR,
  USER;

  @Override
  public String getAuthority() {
    return name();
  }
}
