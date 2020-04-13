package com.skillbox.blog.service;

import com.skillbox.blog.entity.User;
import com.skillbox.blog.entity.enums.Role;
import com.skillbox.blog.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("No user with such email: " + email));
    List<Role> authorities = new ArrayList<>();
    if (user.getIsModerator() == 1) {
      authorities.add(Role.MODERATOR);
    }
    authorities.add(Role.USER);
    user.setAuthorities(authorities);
    return user;
  }

  public User getCurrentUser() {
    Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (user instanceof User) {
      return (User) user;
    } else {
      throw new AuthenticationCredentialsNotFoundException("Session does not exist");
    }

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
