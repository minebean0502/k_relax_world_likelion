package com.example.relaxworld.user.dto;

import com.example.relaxworld.user.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    @Setter
    private String username;
    @Setter
    private String userId;
    @Setter
    private String password;
    @Setter
    private String phoneNumber;
    @Setter
    private String userRole;
    @Getter
    private User entity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities
                = new ArrayList<>();
        String[] rawAuthorities = userRole.split(",");
        for (String rawAuthority : rawAuthorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(rawAuthority));
        }
        return grantedAuthorities;
    }

    public static CustomUserDetails fromEntity(User entity) {
        CustomUserDetails userDetails = new CustomUserDetails();

        userDetails.setUsername(entity.getUsername());
        userDetails.setUserId(entity.getUserId());
        userDetails.setPassword(entity.getPassword());
        userDetails.setPhoneNumber(entity.getPhoneNumber());
        return userDetails;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
