package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestEditProfileDto;
import com.skillbox.blog.dto.response.ResponseRegisterDto;
import com.skillbox.blog.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.instrument.IllegalClassFormatException;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRegisterDto editProfile(@ModelAttribute RequestEditProfileDto request,
                                           ModelMap modelMap) throws IllegalClassFormatException {
        modelMap.addAttribute("request", request);
        return profileService.editProfile(request);
    }
}