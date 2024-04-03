package com.example.relaxworld.user.controller;

import com.example.relaxworld.user.entity.User;
import com.example.relaxworld.user.repository.UserRepository;
import com.example.relaxworld.user.service.JpaUserDetailsManager;
import com.example.relaxworld.user.dto.CustomUserDetails;
import com.example.relaxworld.jwt.dto.JwtRequestDto;
import com.example.relaxworld.jwt.dto.JwtResponseDto;
import com.example.relaxworld.user.entity.ModifyPasswordRequest;
import com.example.relaxworld.user.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final JpaUserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final S3UploadService s3UploadService;

    // 로그인 v1/user/login
    @PostMapping("login")
    public JwtResponseDto login(
            @RequestBody
            JwtRequestDto dto
    ) {
        return manager.login(dto);
    }

    // 회원가입 v1/user/signup
    @PostMapping("signup")
    public String signUp(
            @RequestPart("username")
            String username,
            @RequestPart("userId")
            String userId,
            @RequestPart("password")
            String password,
            @RequestPart("password-check")
            String passwordCheck,
            @RequestPart("phoneNumber")
            String phoneNumber,
            @RequestPart(required = false) MultipartFile img
    ) throws IOException {
        if (password.equals(passwordCheck)){
            manager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .userId(userId)
                    .password(passwordEncoder.encode(password))
                    .phoneNumber(phoneNumber)
                    .image(s3UploadService.uploadFile(img))
                    .build());
            // 회원가입 성공 후 로그인 페이지로 이동
            return "회원가입 성공";
        }
        else{
            // 비밀번호 불일치
            return "비밀번호 불일치";
        }
    }

    // id찾기 페이지 /v1/user/idpw/id/find
    @GetMapping("idpw/id/find")
    public String idFind(
            @RequestParam("phone_number")
            String phoneNumber
    ) {
        return manager.idFind(phoneNumber);
    }


    // pw찾기 페이지 /v1/user/idpw/pw/find
    @GetMapping("idpw/pw/find")
    public String pwFind(
            @RequestParam("userId")
            String userId,
            @RequestParam("phone_number")
            String phoneNumber
    ) {
        return manager.pwFind(userId, phoneNumber);
    }

    // (임시)비밀번호 수정 /v1/user/idpw/modify
    @PostMapping("idpw/modify")
    public String idpwModify(
            @RequestBody
            ModifyPasswordRequest request
    ) {
        return manager.updatePassword(request);
    }

    @PostMapping("image/modify")
    public ResponseEntity<Object> imageModify(
            @RequestPart String userId,
            @RequestPart(required = false) MultipartFile image
    ) throws IOException {
        Map<String, String> map = new HashMap<>();
        try{
            Optional<User> user = userRepository.findByUserId(userId);

            User userEntity = user.get();
            //S3 스토리지 내에서 기존 이미지 삭제
            s3UploadService.deleteFile(userEntity.getImage());
            //User Image에는 이미지의 링크가 들어간다.
            userEntity.setImage(s3UploadService.uploadFile(image));
            userRepository.save(userEntity);

            //추가로 이미지 삽입, 프로필사진 말고 MultipartFile[] 로 여러장 사진을 업로드할 때 코드.
//            if(image != null){
//                List<String> fileNames = new ArrayList<>();
//
//                for(MultipartFile img : image){
//
//                    String filePathAndName = s3UploadService.uploadFile(img);
//                    fileNames.add(filePathAndName);
//                }
//                String dbSaveImages = String.join(",", fileNames);
//
//                userEntity.setImage(dbSaveImages);
//            }

            map.put("result", "사진 등록 성공");
        } catch (Exception e){
            map.put("error", e.toString());
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
