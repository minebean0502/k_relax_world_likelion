package com.example.relaxworld.user.service;

import com.example.relaxworld.user.dto.CustomUserDetails;
import com.example.relaxworld.jwt.dto.JwtRequestDto;
import com.example.relaxworld.jwt.dto.JwtResponseDto;
import com.example.relaxworld.user.entity.ModifyPasswordRequest;
import com.example.relaxworld.user.entity.User;
import com.example.relaxworld.jwt.JwtTokenUtils;
import com.example.relaxworld.user.repository.UserRepository;
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
                    .phoneNumber("admin")
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
                .userRole("REGISTER")
                .build();

        userRepository.save(newUser);
    }


    public JwtResponseDto login(JwtRequestDto dto) {
        User userEntity = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // jwt 발급
        String jwt = jwtTokenUtils.generateToken(CustomUserDetails.fromEntity(userEntity));
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;
    }

    // id 찾기
    public String idFind(String phoneNumber) {
        // phoneNumber를 가진 user 찾기
        User userEntity = userRepository.findByPhoneNumber(phoneNumber);

        // user가 null이면 해당 번호와 일치하는 회원이 없다는 의미
        if (userEntity == null) {
            return "해당 번호와 일치하는 회원이 없습니다.";
        }
        return "아이디: " + userEntity.getUserId();
    }


    // pw 찾기
    public String pwFind(String userId, String phoneNumber) {
        // userId로 회원 찾기
        User userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if(!userEntity.getPhoneNumber().equals(phoneNumber)){
            return "해당 id와 phone number와 일치하지 않습니다.";
        }
        System.out.println("비밀번호: " + userEntity.getPassword());
        return "비밀번호를 재설정해주세요";
    }

    // 입력한 userId랑 회원가입때 입력한 폰 넘버랑 다르면 비밀번호 수정 실패
    public String updatePassword(ModifyPasswordRequest request){
        // login 아이디로 일치하는 회원이 있는지 찾기
        User userEntity = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // 회원가입 때 입력한 핸드폰 번호와 비밀번호 수정을 위해 입력한폰 핸드폰 번호가 일치하지 않으면 bad request
        if (!userEntity.getPhoneNumber().equals(request.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number does not match.");
        }

        // userid, phonenumber 모두 일치하면 비밀번호 수정
        userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(userEntity);

        return "비밀번호 수정 완료";
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
