package com.pividori.veterinaria.securitys;

import com.pividori.veterinaria.models.Permission;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomerUserDetails implements UserDetails {

    private final User user;
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    public CustomerUserDetails(User user){

        this.user = user;

        for (Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())));

            for (Permission permission : role.getPermission()){
                authorities.add(new SimpleGrantedAuthority("PERM".concat(permission.getName())));
            }
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
