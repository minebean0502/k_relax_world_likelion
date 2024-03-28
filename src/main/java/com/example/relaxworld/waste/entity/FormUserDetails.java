package com.example.relaxworld.waste.entity;

import com.example.relaxworld.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

//@Getter
@Builder
// @Entity
@NoArgsConstructor
@AllArgsConstructor
public class FormUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String userId;
    private String userRole;

    @Getter
    private User entity;

    public static FormUserDetails fromEntity(User entity) {
        return FormUserDetails.builder()
                .entity(entity)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return Arrays.stream(entity.getUserRole().split(","))
                .map(role -> (GrantedAuthority) () -> role)
                .toList();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.entity.getPassword();
    }

    public Long getId() {
        return this.entity.getId();
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


