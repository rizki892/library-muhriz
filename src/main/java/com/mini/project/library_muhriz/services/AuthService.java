package com.mini.project.library_muhriz.services;


import com.mini.project.library_muhriz.dto.request.LoginRequest;
import com.mini.project.library_muhriz.dto.request.RegisterRequest;
import com.mini.project.library_muhriz.models.Role;
import com.mini.project.library_muhriz.models.User;
import com.mini.project.library_muhriz.repositories.UserRepository;
import com.mini.project.library_muhriz.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email sudah terdaftar!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return "User berhasil didaftarkan!";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email atau password salah!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email atau password salah!");
        }

        return jwtUtil.generateToken(user.getEmail(),user.getRole().name());
    }
}

