package com.skillbox.blog.service;

import com.skillbox.blog.config.Mail;
import com.skillbox.blog.dto.request.RequestPasswordDto;
import com.skillbox.blog.dto.request.RequestPwdRestoreDto;
import com.skillbox.blog.dto.request.RequestUserDto;
import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.dto.response.ResponseLoginDto;
import com.skillbox.blog.dto.response.ResponsePasswordDto;
import com.skillbox.blog.dto.response.ResponseRegisterDto;
import com.skillbox.blog.entity.CaptchaCode;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.exception.InvalidCaptchaException;
import com.skillbox.blog.mapper.CaptchaToCaptchaDto;
import com.skillbox.blog.mapper.UserDtoToUser;
import com.skillbox.blog.mapper.UserToResponseLoginDto;
import com.skillbox.blog.repository.CaptchaRepository;
import com.skillbox.blog.repository.UserRepository;
import com.skillbox.blog.utils.RandomStringGenerator;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AuthService {

  private UserRepository userRepository;
  private CaptchaRepository captchaRepository;
  private CaptchaToCaptchaDto captchaToCaptchaDto;
  private UserDtoToUser userDtoToUser;
  private UserToResponseLoginDto userToResponseLoginDto;
  private MailService mailServer;

  public ResponseRegisterDto registerNewUser(RequestUserDto user) {
    String captchaCodeByCode = captchaRepository.findCaptchaCodeByCode(user.getCaptcha());
    if (captchaCodeByCode != null) {
      userRepository.save(userDtoToUser.map(user));
      return ResponseRegisterDto
          .builder()
          .result(true)
          .build();
    }
    throw new InvalidCaptchaException("captcha argument exception");
  }

  public ResponseCaptchaDto genAndSaveCaptcha() throws IOException {
    ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
    cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
    cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));

    CaptchaCode captchaCode;
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      String decodeCode = EncoderHelper.getChallangeAndWriteImage(cs, "png", byteArrayOutputStream);
      String encodeCode = "data:image/png;base64,";
      encodeCode += Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
      captchaCode =
          new CaptchaCode()
              .setCode(decodeCode)
              .setSecretCode(encodeCode)
              .setTime(LocalDateTime.now());
    }
    captchaRepository.save(captchaCode);
    return captchaToCaptchaDto.map(captchaCode);
  }

  @Transactional(readOnly = true)
  public ResponseLoginDto checkAuth(HttpServletRequest request) {
    if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
      throw new AuthenticationCredentialsNotFoundException(
          "Session does not exist: " + request.getHeader("Cookie"));
    }
    return ResponseLoginDto.builder()
        .result(true)
        .user(
            userToResponseLoginDto.map(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
        .build();
  }

  public ResponsePasswordDto restorePassword(RequestPwdRestoreDto dto, String host) {
    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new EntityNotFoundException("There is no such user " + dto.getEmail()));
    user.setCode(RandomStringGenerator.randomString(45).toLowerCase());
    userRepository.save(user);
    mailServer.sendMail(
        new Mail(
            "noreply@lifo.ml",
            dto.getEmail(),
            "Password restoration",
            String.format(
                "Hello, %1$s!\n"
                    + "Please follow the next link to restore pwd:\n"
                    + "%2$s\n\n"
                    + "/r Awesome blog team",
                user.getName(),
                "http://" + host + "/login/change-password/?hash" + user.getCode()
            )
        )
    );
    return new ResponsePasswordDto(true);
  }

  public ResponsePasswordDto changePassword(RequestPasswordDto dto) {
    User user = userRepository.findByCode(dto.getCode())
        .orElseThrow(EntityNotFoundException::new);
    String captchaCodeByCode = captchaRepository.findCaptchaCodeByCode(dto.getCaptcha());
    if (captchaCodeByCode != null) {
      user.setPassword(dto.getPassword());
      userRepository.save(user);
      return new ResponsePasswordDto(true);
    }
    throw new InvalidCaptchaException("captcha argument exception");
  }
}
