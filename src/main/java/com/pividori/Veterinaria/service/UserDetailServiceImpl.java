package com.pividori.Veterinaria.service;

import com.pividori.Veterinaria.model.User;
import com.pividori.Veterinaria.repository.IUserRepository;
import com.pividori.Veterinaria.security.CustomerUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (user.getRoles() == null || user.getRoles().isEmpty()){
            throw new IllegalStateException("User has no roles: " + username);
        }

        return new CustomerUserDetails(user);

    }
}

