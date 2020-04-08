package com.skillbox.blog.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillbox.blog.dto.request.RequestLoginDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JsonReaderAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String ERR_MSG = "Cannot parse login request body";
    private final ObjectMapper mapper;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        RequestLoginDto loginRequest = getLoginRequest(request);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    private RequestLoginDto getLoginRequest(HttpServletRequest request) {
        RequestLoginDto loginRequest = null;
        try (BufferedReader reader = request.getReader()) {
            StringBuffer sb = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            loginRequest = mapper.readValue(sb.toString(), RequestLoginDto.class);
        } catch (IOException e) {
            log.error(ERR_MSG);
            throw new InternalAuthenticationServiceException(ERR_MSG);
        }
        return loginRequest;
    }
}
