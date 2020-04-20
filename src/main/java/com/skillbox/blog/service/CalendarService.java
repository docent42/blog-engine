package com.skillbox.blog.service;

import com.skillbox.blog.dto.response.ResponseCalendarDto;
import com.skillbox.blog.repository.PostRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CalendarService {

  PostRepository postRepository;

  public ResponseCalendarDto getPublicationsCount(int year) {
    if (String.valueOf(year).length() != 4 || year == 0) {
      year = LocalDate.now().getYear();
    }

    List<Integer> years = postRepository.findYearsWherePublicationsPresent();
    ArrayList<String> postDateList = postRepository.findCountPublicationsOnDateByYear(year);

    TreeMap<String, Integer> posts = new TreeMap<>();
    for (String postDate : postDateList) {
      if (posts.containsKey(postDate)) {
        posts.put(postDate, posts.get(postDate) + 1);
      } else {
        posts.put(postDate, 1);
      }
    }

    return ResponseCalendarDto.builder()
        .years(years)
        .posts(posts)
        .build();
  }
}
