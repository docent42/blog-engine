package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestEditProfileDto;
import com.skillbox.blog.dto.response.ResponseRegisterDto;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.HashSet;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public ResponseRegisterDto editProfile(RequestEditProfileDto request)
            throws IllegalClassFormatException {
        HashMap<String, String> errors = new HashMap<>();
        User user = userService.getCurrentUser();
        String email = request.getEmail();
        String name = request.getName();
        String password = request.getPassword();

        checkEmail(email, errors);
        checkName(name, errors, user);
        checkPassword(password, errors, user);

        if (errors.isEmpty()) {
            saveChanges(request, user);
            return new ResponseRegisterDto().builder()
                    .result(true)
                    .build();
        }

        return new ResponseRegisterDto().builder()
                .result(false)
                .errors(errors)
                .build();
    }

    private void checkEmail(String email, HashMap<String, String> errors) {
        HashSet<String> emails = userRepository.findAllEmails();
        if (emails.contains(email)) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }
    }

    private void checkName(String name, HashMap<String, String> errors, User user) {
        if (name.equals(user.getName())) {
            errors.put("name", "Имя указано неверно");
        }
    }

    private void checkPassword(String password, HashMap<String, String> errors, User user) {
        if (password.length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        } else if (!bCryptPasswordEncoder.matches(user.getPassword(), password)) {
            errors.put("password", "Пароли совпадают");
        }
    }

    private void saveChanges(RequestEditProfileDto request, User user)
            throws IllegalClassFormatException {
        user.setEmail(request.getEmail());

        if (request.getRemovePhoto() == 1) {
            File oldPhoto = new File(user.getPhoto());
            oldPhoto.delete();
            user.setPhoto(null);
        } else if (!request.getPhoto().isEmpty()) {
            File oldPhoto = new File(user.getPhoto());
            oldPhoto.delete();
            user.setPhoto(imageService.uploadImage(request.getPhoto()));
        }

        user.setName(request.getName());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}