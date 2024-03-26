package com.example.relaxworld;

import com.example.relaxworld.dto.CustomUserDetails;
import com.example.relaxworld.dto.JwtRequestDto;
import com.example.relaxworld.dto.JwtResponseDto;
import com.example.relaxworld.entity.User;
import com.example.relaxworld.jwt.JwtTokenUtils;
import com.example.relaxworld.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
                    .userRole("ADMIN") // TODO 추후 관리자 role 어떻게 할 지 상의할것 우선 register로 설정
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
        // 권한 이름에서 "ROLE_" 접두사 제거
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getUserRole().replace("ROLE_", ""));

        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities);

        /* 0326 ROLE 문제 생겨서 일단 바꿈
        User userEntity = optionalUser.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getUserRole().replace("ROLE_", ""))
                .build();

         */
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
                .userRole("REGISTER")
                .build();

        userRepository.save(newUser);
    }


    public JwtResponseDto login(JwtRequestDto dto) {
        System.out.println("로그인 메서드 시작");
        User userEntity = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        System.out.println("id에 대한 사용자는 찾음 %s".formatted(userEntity.getUserId()));

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            System.out.println("비밀번호 불일치 ");
            System.out.println("dto password %s".formatted(passwordEncoder.encode(dto.getPassword())));
            System.out.println("user entity password %s".formatted(passwordEncoder.encode(userEntity.getPassword())));
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
