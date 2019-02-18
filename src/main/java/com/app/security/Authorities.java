package com.app.security;

import com.app.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public interface Authorities {
    static Collection<GrantedAuthority> getAuthorities(Role role) {
        return Arrays.asList(new SimpleGrantedAuthority(role.getFullName()));
    }
}
