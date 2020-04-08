package com.skillbox.blog.controller;

import com.skillbox.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.lang.instrument.IllegalClassFormatException;

@RestController
@RequestMapping(value = "/api", produces = MediaType.IMAGE_JPEG_VALUE)
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public String uploadImage(@RequestBody MultipartFile uploadFile)
            throws IllegalClassFormatException {
        return imageService.uploadImage(uploadFile);
    }
}