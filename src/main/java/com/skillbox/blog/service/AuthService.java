package com.skillbox.blog.service;

import com.skillbox.blog.dto.request.RequestLoginDto;
import com.skillbox.blog.dto.request.RequestUserDto;
import com.skillbox.blog.dto.response.ResponseCaptchaDto;
import com.skillbox.blog.dto.response.ResponseLogOutDto;
import com.skillbox.blog.dto.response.ResponseLoginDto;
import com.skillbox.blog.dto.response.ResponseRegisterDto;
import com.skillbox.blog.entity.CaptchaCode;
import com.skillbox.blog.entity.User;
import com.skillbox.blog.mapper.CaptchaToCaptchaDto;
import com.skillbox.blog.mapper.UserDtoToUser;
import com.skillbox.blog.mapper.UserToResponseLoginDto;
import com.skillbox.blog.repository.CaptchaRepository;
import com.skillbox.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

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

    public ResponseRegisterDto registerNewUser(RequestUserDto user) {
        userRepository.save(userDtoToUser.map(user));
        return ResponseRegisterDto
                .builder()
                .result(true)
                .build();
    }

    public ResponseCaptchaDto genAndSaveCaptcha() throws IOException {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));

        CaptchaCode captchaCode;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            String decodeCode = EncoderHelper.getChallangeAndWriteImage(cs, "png", byteArrayOutputStream);
            String encodeCode = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            captchaCode = new CaptchaCode()
                    .setCode(decodeCode)
                    .setSecretCode(encodeCode)
                    .setTime(LocalDateTime.now());
        }
        captchaRepository.save(captchaCode);
        return captchaToCaptchaDto.map(captchaCode);
    }

    @Transactional(readOnly = true)
    public ResponseLoginDto loginUser(HttpServletRequest request, RequestLoginDto requestLoginDto) {
        User existsUser = userRepository.findByEmailAndPassword(requestLoginDto.getEmail(),
                requestLoginDto.getPassword());
        if (Objects.nonNull(existsUser)) {
            request.getSession().setAttribute("user", existsUser.getId());
            return ResponseLoginDto
                    .builder()
                    .result(true)
                    .user(userToResponseLoginDto.map(existsUser))
                    .build();
        } else {
            return ResponseLoginDto
                    .builder()
                    .result(false)
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseLogOutDto closedSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(request.getSession().getAttribute("user").toString());
        return ResponseLogOutDto.builder()
                .result(true)
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseLoginDto checkAuth(HttpServletRequest request) {
        User user = userRepository.findById(Long.valueOf(request.getSession().getId())).get();
        if (Objects.nonNull(user)) {
            return ResponseLoginDto
                    .builder()
                    .result(true)
                    .user(userToResponseLoginDto.map(user))
                    .build();
        } else {
            return ResponseLoginDto
                    .builder()
                    .result(false)
                    .build();
        }
    }
}
