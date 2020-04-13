package com.skillbox.blog.controller;

import com.skillbox.blog.dto.request.RequestPasswordDto;
import com.skillbox.blog.dto.request.RequestPwdRestoreDto;
import com.skillbox.blog.dto.request.RequestUserDto;
import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.service.AuthService;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

  private AuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults register(@Valid @RequestBody RequestUserDto user) {
    return authService.registerNewUser(user);
  }

  @GetMapping("/captcha")
  @ResponseStatus(HttpStatus.OK)
  public ResponseCaptchaDto genCaptcha() throws IOException {
    return authService.genAndSaveCaptcha();
  }

  @GetMapping("/check")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults check(HttpServletRequest request, Principal principal) {
    return authService.checkAuth(request, principal);
  }

  @PostMapping("/restore")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults restorePassword(@Valid @RequestBody RequestPwdRestoreDto dto,
      @RequestHeader String host) {
    return authService.restorePassword(dto, host);
  }

  @PostMapping("/password")
  @ResponseStatus(HttpStatus.OK)
  public ResponseResults changePassword(@Valid @RequestBody RequestPasswordDto dto) {
    return authService.changePassword(dto);
  }

}
