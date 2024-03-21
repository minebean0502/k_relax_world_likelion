package com.example.relaxworld;

import com.example.relaxworld.dto.CustomUserDetails;
import com.example.relaxworld.dto.JwtRequestDto;
import com.example.relaxworld.dto.JwtResponseDto;
import com.example.relaxworld.entity.User;
import com.example.relaxworld.jwt.JwtTokenUtils;
import com.example.relaxworld.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;


    public JpaUserDetailsManager (
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenUtils jwtTokenUtils
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtils = jwtTokenUtils; // JwtTokenUtils 주입

        // 이미 관리자 계정이 존재하는지 확인하고 없으면
        if (!userExists("admin")) {
            // 관리자 계정 생성
            createUser(CustomUserDetails.builder()
                    .username("admin")
                    .userId("admin")
                    .password(passwordEncoder.encode("admin"))
                    .userRole("ROLE_REGISTER") // TODO 추후 관리자 role 어떻게 할 지 상의할것 우선 register로 설정
                    .build());
        }
    }

    @Override
    // Spring Security에서 인증을 처리할 때 사용하는 메서드
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> optionalUser
                = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);

        User userEntity = optionalUser.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getUserRole())
                .build();
    }

    @Override
    public void createUser(UserDetails user) {
        if (!(user instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("UserDetails must be an instance of CustomUserDetails");
        }

        CustomUserDetails userDetails = (CustomUserDetails) user;

        if (userRepository.existsByUsername(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        User newUser = User.builder()
                .username(userDetails.getUsername())
                .userId(userDetails.getUserId())
                .password(userDetails.getPassword())
                .phoneNumber(userDetails.getPhoneNumber())
                .userRole("ROLE_REGISTER")
                .build();

        userRepository.save(newUser);
    }


    public JwtResponseDto login(JwtRequestDto dto) {
        User userEntity = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // jwt 발급
        String jwt = jwtTokenUtils.generateToken(CustomUserDetails.fromEntity(userEntity));
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
