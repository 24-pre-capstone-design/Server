package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.OwnerRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Owner implements UserDetails {

    @Id
    private String id;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Owner(String id, String password, Role role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public static Owner of(OwnerRequestDto ownerRequestDto, PasswordEncoder passwordEncoder) {
        return Owner.builder()
                .id(ownerRequestDto.getId())
                .password(passwordEncoder.encode(ownerRequestDto.getPassword()))
                .name(ownerRequestDto.getName())
                .role(Role.USER)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(getRole().getKey()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
