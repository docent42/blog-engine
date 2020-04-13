package com.skillbox.blog.service;

import com.skillbox.blog.config.Mail;
import com.skillbox.blog.dto.request.RequestPasswordDto;
import com.skillbox.blog.dto.request.RequestPwdRestoreDto;
import com.skillbox.blog.dto.request.RequestUserDto;
import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.dto.response.ResponseLoginDto;
import com.skillbox.blog.dto.response.ResponsePasswordDto;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.entity.CaptchaCode;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.exception.InvalidAttributeException;
import com.skillbox.blog.exception.InvalidCaptchaException;
import com.skillbox.blog.mapper.CaptchaToCaptchaDto;
import com.skillbox.blog.mapper.UserDtoToUser;
import com.skillbox.blog.mapper.UserToResponseLoginDto;
import com.skillbox.blog.repository.CaptchaRepository;
import com.skillbox.blog.repository.UserRepository;
import com.skillbox.blog.utils.RandomStringGenerator;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private PasswordEncoder passwordEncoder;

  public ResponseResults registerNewUser(RequestUserDto dto) {
    String captchaCodeByCode = captchaRepository.findCaptchaCodeByCode(dto.getCaptcha());

    if (captchaCodeByCode == null) {
      throw new InvalidAttributeException(Map.of("captcha", "Wrong captcha code entered"));
    }
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      throw new InvalidAttributeException(Map.of("email", "Email address already registered"));
    }
    if (dto.getPassword().length() < 8) {
      throw new InvalidAttributeException(Map.of("password", "Pwd is shorter than 8 symbols"));
    }
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    User user = userDtoToUser.map(dto);
    // due to frontend doesn't send name
    user.setName(user.getEmail());
    // due to frontend cannot handle null
    user.setPhoto("");
    userRepository.save(user);
    return new ResponseResults<>()
        .setResult(true);
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
    } catch (IOException e) {
      throw new InvalidCaptchaException("Cannot generate captcha");
    }
    captchaRepository.save(captchaCode);
    return captchaToCaptchaDto.map(captchaCode);
  }

  @Transactional(readOnly = true)
  public ResponseResults checkAuth(HttpServletRequest request, Principal principal) {
    if (principal == null) {
      throw new AuthenticationCredentialsNotFoundException(
          "Session does not exist: " + request.getHeader("Cookie"));
    }
    return new ResponseLoginDto<>()
        .setUser(
            userToResponseLoginDto
                .map((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()))
        .setResult(true);
  }

  public ResponseResults restorePassword(RequestPwdRestoreDto dto, String host) {
    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
            "There is no such user " + dto.getEmail()));
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
    return new ResponsePasswordDto<>().setResult(true);
  }

  public ResponseResults changePassword(RequestPasswordDto dto) {
    User user = userRepository.findByCode(dto.getCode())
        .orElseThrow(EntityNotFoundException::new);
    String captchaCodeByCode = captchaRepository.findCaptchaCodeByCode(dto.getCaptcha());
    if (captchaCodeByCode != null) {
      user.setPassword(passwordEncoder.encode(dto.getPassword()));
      userRepository.save(user);
      return new ResponsePasswordDto<>().setResult(true);
    }
    throw new InvalidCaptchaException("captcha argument exception");
  }
}
