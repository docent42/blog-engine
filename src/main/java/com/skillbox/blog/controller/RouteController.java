package com.skillbox.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {

  @RequestMapping(value = {
      "/edit/*",
      "/calendar/*",
      "/my/*",
      "/login",
      "/login/**",
      "/moderator/*",
      "/moderation/*",
      "/post/*",
      "/posts/*",
      "/profile",
      "settings",
      "/stat",
      "/404"
  })
  public String frontend() {
    return "forward:/";
  }
}