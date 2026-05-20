package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.dto.request.*;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.entity.User;
import com.sharafatdin.talgatbek.taskmanager.exception.TalgatbekBadRequestException;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekUserMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.UserRepository;
import com.sharafatdin.talgatbek.taskmanager.security.jwt.TalgatbekJwtUtils;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author Talgatbek Sharafatdin */
@Service
@RequiredArgsConstructor
@Slf4j
public class TalgatbekAuthServiceImpl implements TalgatbekAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TalgatbekJwtUtils jwtUtils;
    private final TalgatbekUserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new TalgatbekBadRequestException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new TalgatbekBadRequestException("Email already in use: " + request.getEmail());
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(User.Role.ROLE_USER)
                .build();
        User saved = userRepository.save(user);
        log.info("User registered: {}", saved.getUsername());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                saved.getUsername(), saved.getPassword(),
                java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(saved.getRole().name())));
        return AuthResponse.builder()
                .accessToken(jwtUtils.generateToken(userDetails))
                .refreshToken(jwtUtils.generateRefreshToken(userDetails))
                .tokenType("Bearer")
                .user(userMapper.toResponse(saved))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new TalgatbekBadRequestException("User not found"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole().name())));
        log.info("User logged in: {}", user.getUsername());
        return AuthResponse.builder()
                .accessToken(jwtUtils.generateToken(userDetails))
                .refreshToken(jwtUtils.generateRefreshToken(userDetails))
                .tokenType("Bearer")
                .user(userMapper.toResponse(user))
                .build();
    }
}
