package com.pre_capstone_design_24.server.global.auth;

import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Owner owner =  ownerRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
        return new Owner(owner.getId(), owner.getPassword(), owner.getRole());
    }

}
