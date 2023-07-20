package com.lgutierrez.blogrestapi.service.impl;

import com.lgutierrez.blogrestapi.entity.Role;
import com.lgutierrez.blogrestapi.entity.User;
import com.lgutierrez.blogrestapi.exception.BlogAPIException;
import com.lgutierrez.blogrestapi.payload.LoginDto;
import com.lgutierrez.blogrestapi.payload.RegisterDto;
import com.lgutierrez.blogrestapi.repository.RoleRepository;
import com.lgutierrez.blogrestapi.repository.UserRepository;
import com.lgutierrez.blogrestapi.security.JwtTokenProvider;
import com.lgutierrez.blogrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        //Add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username is already exist!");

        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email is already exist!");

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);

        User user = new User(null,
                             registerDto.getName(),
                             registerDto.getUsername(),
                             registerDto.getEmail(),
                             passwordEncoder.encode(registerDto.getPassword()),
                             roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
