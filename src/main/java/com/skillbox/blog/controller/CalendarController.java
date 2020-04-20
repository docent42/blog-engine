package com.skillbox.blog.controller;

import com.skillbox.blog.dto.response.ResponseCalendarDto;
import com.skillbox.blog.service.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CalendarController {

  CalendarService calendarService;

  @GetMapping("/calendar")
  @ResponseStatus(HttpStatus.OK)
  public ResponseCalendarDto getPublicationsCount(
      @RequestParam(value = "year", defaultValue = "0") int year) {
    return calendarService.getPublicationsCount(year);
  }
}
