package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.response.UserResponse;
import java.util.List;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekUserService {
    UserResponse getCurrentUser(String username);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
}
