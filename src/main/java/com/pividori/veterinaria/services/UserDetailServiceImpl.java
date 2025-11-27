package com.pividori.veterinaria.services;

import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.repositorys.UserRepository;
import com.pividori.veterinaria.securitys.CustomerUserDetails;
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

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (user.getRole() == null){
            throw new IllegalStateException("User has no roles: " + username);
        }

        return new CustomerUserDetails(user);

    }
}

