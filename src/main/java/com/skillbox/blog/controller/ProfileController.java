package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestEditProfileDto;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.service.ProfileService;
import java.lang.instrument.IllegalClassFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController {

  @Autowired
  private ProfileService profileService;

  @PostMapping("/my")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults editProfile(
      @RequestParam(value = "photo", required = false) MultipartFile file,
      @ModelAttribute RequestEditProfileDto request
  ) throws IllegalClassFormatException {
    return profileService.editProfile(request, file);
  }
}