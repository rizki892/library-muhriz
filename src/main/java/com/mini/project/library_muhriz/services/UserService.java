package com.mini.project.library_muhriz.services;


import com.mini.project.library_muhriz.models.User;
import com.mini.project.library_muhriz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

