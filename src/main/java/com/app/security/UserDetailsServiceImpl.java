package com.app.security;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("USERNAME IS NULL"));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    true, true, true, true,
                    Authorities.getAuthorities(user.getRole())
            );

        } catch (Exception e) {
            throw new MyException(ExceptionCode.SECURITY, e.getMessage());
        }
    }
}
