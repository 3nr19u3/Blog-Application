package com.lgutierrez.blogrestapi.service;

import com.lgutierrez.blogrestapi.payload.LoginDto;
import com.lgutierrez.blogrestapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
