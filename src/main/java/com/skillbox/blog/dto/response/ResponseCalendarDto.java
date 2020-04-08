package com.skillbox.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.TreeMap;

@Data
@AllArgsConstructor
public class ResponseCalendarDto {

    @JsonProperty("years")
    private Object[] years;

    @JsonProperty("posts")
    private TreeMap<String, Integer> posts;
}