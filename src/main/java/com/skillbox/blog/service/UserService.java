package com.skillbox.blog.service;

import com.skillbox.blog.entity.User;
import com.skillbox.blog.entity.enums.Role;
import com.skillbox.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user =  userRepository
        .findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("No user with such email: " + email));
    List<Role> authorities = new ArrayList<>();
    if (user.getIsModerator() == 1) {
      authorities.add(Role.MODERATOR);
    }
    authorities.add(Role.USER);
    user.setAuthorities(authorities);
    return user;
  }

  public User getCurrentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("No user with such email: " + email));
  }

  public User getModerator() {
    return userRepository.findByIsModerator((byte) 1)
            .orElseThrow(() -> new EntityNotFoundException("Moderator is not defined."));
  }

  public boolean isModerator() {
    return SecurityContextHolder.getContext()
        .getAuthentication().getAuthorities().contains(Role.MODERATOR);
  }
}
