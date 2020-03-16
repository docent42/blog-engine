package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestLoginDto;
import com.skillbox.blog.dto.request.RequestPasswordDto;
import com.skillbox.blog.dto.request.RequestUserDto;
import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.dto.response.ResponseLogOutDto;
import com.skillbox.blog.dto.response.ResponseLoginDto;
import com.skillbox.blog.dto.response.ResponseRegisterDto;
import com.skillbox.blog.dto.response.ResponseUserDto;
import com.skillbox.blog.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRegisterDto register(@Valid @RequestBody RequestUserDto user) {
        return authService.registerNewUser(user);
    }

    @GetMapping("/captcha")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCaptchaDto genCaptcha() throws IOException {
        return authService.genAndSaveCaptcha();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDto login(HttpServletRequest request, @RequestBody RequestLoginDto requestLoginDto) {
        return authService.loginUser(request, requestLoginDto);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLogOutDto logOut(HttpServletRequest request) {
        return authService.closedSession(request);
    }

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDto check(HttpServletRequest request) {
        return authService.checkAuth(request);
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDto restorePassword(@Valid @RequestBody RequestPasswordDto passwordRequestDto){
        return null;
    }

}
