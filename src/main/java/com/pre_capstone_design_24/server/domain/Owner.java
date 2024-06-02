package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.OwnerRequestDto;
import com.pre_capstone_design_24.server.requestDto.OwnerUpdateDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
public class Owner implements UserDetails {

    @Id
    private String id;

    private String password;

    private String name;

    private String birthDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Employee> employees;

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
                .birthDate(ownerRequestDto.getBirthDate())
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

    public void update(OwnerUpdateDto ownerUpdateDto) {
        this.name = ownerUpdateDto.getName();
        this.birthDate = ownerUpdateDto.getBirthDate();
    }

    public void updatePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

}
