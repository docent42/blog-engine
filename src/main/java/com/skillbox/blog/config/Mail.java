package com.skillbox.blog.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {

    private String from;
    private String to;
    private String subject;
    private String content;
}
