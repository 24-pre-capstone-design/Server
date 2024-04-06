package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.OwnerCreateRequestDto;
import com.pre_capstone_design_24.server.requestDto.OwnerUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
@Setter
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

    @Transient
    private String newPassword;

    public Owner(String id, String password, Role role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public static Owner of(OwnerCreateRequestDto ownerCreateRequestDto, PasswordEncoder passwordEncoder) {
        return Owner.builder()
                .id(ownerCreateRequestDto.getId())
                .password(passwordEncoder.encode(ownerCreateRequestDto.getPassword()))
                .name(ownerCreateRequestDto.getName())
                .role(Role.USER)
                .build();
    }

    public static Owner updateOf(OwnerUpdateRequestDto ownerUpdateRequestDto, PasswordEncoder passwordEncoder) {
        return Owner.builder()
            .id(ownerUpdateRequestDto.getId())
            .password(passwordEncoder.encode(ownerUpdateRequestDto.getPassword()))
            .name(ownerUpdateRequestDto.getName())
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

    public void updateName(String name) {
        this.name = name;
    }

    public void update(OwnerUpdateRequestDto ownerCreateRequestDto) {
        updateName(ownerCreateRequestDto.getName());
    }
}
