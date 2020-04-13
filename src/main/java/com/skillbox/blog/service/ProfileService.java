package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestEditProfileDto;
import com.skillbox.blog.dto.response.ResponseResults;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.exception.InvalidAttributeException;
import com.skillbox.blog.repository.UserRepository;
import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ProfileService {

  private UserRepository userRepository;
  private UserService userService;
  private ImageService imageService;
  private PasswordEncoder passwordEncoder;

  public ResponseResults editProfile(RequestEditProfileDto request, MultipartFile file)
      throws IllegalClassFormatException {
    Map<String, String> errors = new LinkedHashMap<>();
    User user = userService.getCurrentUser();
    String email = request.getEmail();
    String name = request.getName();
    String password = request.getPassword();

    checkEmail(email, errors);
    checkName(name, errors);
    checkPassword(password, user.getPassword(), errors);
    checkPhoto(request, user, file, errors);

    if (!errors.isEmpty()) {
      throw new InvalidAttributeException(errors);
    }

    user.setEmail(email);
    user.setName(name);
    if (password != null) {
      user.setPassword(passwordEncoder.encode(password));
    }
    userRepository.save(user);
    return new ResponseResults<>()
        .setResult(true);
  }

  private void checkEmail(String email, Map<String, String> errors) {
    if (email == null) {
      errors.putAll(Map.of("email", "Email is null"));
    }
    if (SecurityContextHolder.getContext().getAuthentication().getName().equals(email)) {
      return;
    }
    HashSet<String> emails = userRepository.findAllEmails();
    if (emails.contains(email)) {
      errors.putAll(Map.of("email", "Email address already registered"));
    }
  }

  private void checkName(String name, Map<String, String> errors) {
    if (name == null || name.isEmpty()) {
      errors.putAll(Map.of("name", "Name is not valid"));
    }
  }

  private void checkPassword(String pwd, String currentPwd, Map<String, String> errors) {
    if (pwd != null) {
      if (pwd.length() < 8) {
        errors.putAll(Map.of("password", "Pwd is shorter than 8 symbols"));
      } else if (passwordEncoder.matches(pwd, currentPwd)) {
        errors.putAll(Map.of("password", "Password unchanged"));
      }
    }
  }

  private void checkPhoto(RequestEditProfileDto request, User user, MultipartFile file,
      Map<String, String> errors)
      throws IllegalClassFormatException {

    if (request.getRemovePhoto() != null && request.getRemovePhoto() == 1) {
      File oldPhoto = new File(user.getPhoto());
      oldPhoto.delete();
      user.setPhoto("");
    } else if (file != null && !file.isEmpty()) {
      if (file.getSize() > 5 * 1024 * 1024) {
        errors.putAll(Map.of("photo", "File too large"));
      }
      File oldPhoto = new File(user.getPhoto());
      oldPhoto.delete();
      user.setPhoto("/" + imageService.uploadImage(file));
    }
  }
}