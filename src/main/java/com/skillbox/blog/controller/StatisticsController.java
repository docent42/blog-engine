package com.skillbox.blog.controller;

import com.skillbox.blog.dto.response.ResponseStatisticsDto;
import com.skillbox.blog.service.StatisticsService;
import java.nio.file.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class StatisticsController {

  @Autowired
  StatisticsService statisticsService;

  @RequestMapping("statistics/my")
  @ResponseStatus(HttpStatus.OK)
  public ResponseStatisticsDto getStatisticsForCurrentUser() {
    return statisticsService.getStatisticsForCurrentUser();
  }

  @RequestMapping("statistics/all")
  @ResponseStatus(HttpStatus.OK)
  public ResponseStatisticsDto getStatisticForAll() throws AccessDeniedException {
    return statisticsService.getStatisticForAll();
  }
}