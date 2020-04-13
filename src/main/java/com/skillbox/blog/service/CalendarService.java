package com.skillbox.blog.service;

import com.skillbox.blog.dto.response.ResponseCalendarDto;
import com.skillbox.blog.repository.PostRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CalendarService {

  private PostRepository repository;

  public ResponseCalendarDto getPublicationsCount(int year) {
    if (String.valueOf(year).length() > 4 || year == 0) {
      year = LocalDate.now().getYear();
    }

    Object[] years = repository.findYearsWherePublicationsPresent().toArray();
    Arrays.sort(years, Collections.reverseOrder());

    ArrayList<String> postDateList = repository.findCountPublicationsOnDateByYear(year);

    TreeMap<String, Integer> posts = new TreeMap<>();
    for (String postDate : postDateList) {
      if (posts.containsKey(postDate)) {
        posts.put(postDate, posts.get(postDate) + 1);
      } else {
        posts.put(postDate, 1);
      }
    }

    return new ResponseCalendarDto(years, posts);
  }
}