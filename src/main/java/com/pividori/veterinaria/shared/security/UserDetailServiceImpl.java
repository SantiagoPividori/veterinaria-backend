package com.pividori.veterinaria.shared.security;

import com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity.UserEntity;
import com.pividori.veterinaria.saas.iam.infrastructure.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        if (user.getRole() == null) {
            throw new IllegalStateException("User has no roles: " + username);
        }

        return new CustomUserDetails(user);

    }
}

