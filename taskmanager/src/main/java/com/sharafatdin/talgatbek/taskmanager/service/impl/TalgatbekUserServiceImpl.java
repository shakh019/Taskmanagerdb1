package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.dto.response.UserResponse;
import com.sharafatdin.talgatbek.taskmanager.exception.TalgatbekResourceNotFoundException;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekUserMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.UserRepository;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@Service @RequiredArgsConstructor @Slf4j
public class TalgatbekUserServiceImpl implements TalgatbekUserService {
    private final UserRepository userRepository;
    private final TalgatbekUserMapper userMapper;

    @Override
    public UserResponse getCurrentUser(String username) {
        return userMapper.toResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User", 0L)));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User", id)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) throw new TalgatbekResourceNotFoundException("User", id);
        userRepository.deleteById(id);
        log.info("Deleted user id: {}", id);
    }
}
