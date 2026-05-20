package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.request.*;
import com.sharafatdin.talgatbek.taskmanager.dto.response.AuthResponse;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
