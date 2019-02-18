package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(User user) {
        try {
            if (user == null) {
                throw new NullPointerException("USER IS NULL");
            }

            if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new IllegalArgumentException("USERNAME ALREADY EXISTS");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);

        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
