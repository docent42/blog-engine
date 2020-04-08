package com.skillbox.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class RequestEditProfileDto {

    private MultipartFile photo;
    private byte removePhoto;
    private String name;
    private String email;
    private String password;
}