package com.example.relaxworld;

import com.example.relaxworld.user.dto.CustomUserDetails;
import com.example.relaxworld.user.repository.UserRepository;
import com.example.relaxworld.waste.entity.FormUserDetails;
import com.example.relaxworld.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade {
    @Autowired
    private UserRepository userRepository;

    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User extractUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 현재 인증된 사용자의 이름(Username)

        Optional<User> user = userRepository.findByUsername(currentUsername);

        return user.orElseThrow(() -> new UsernameNotFoundException("User을 찾을 수 없습니다"));
    }
}