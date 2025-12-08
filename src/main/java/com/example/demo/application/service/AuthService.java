package com.example.demo.application.service;

import com.example.demo.application.dto.LoginDTO;
import com.example.demo.application.dto.RegisterDTO;
import com.example.demo.application.dto.TokenDTO;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.config.JwtService;
import com.example.demo.config.UserDetailsServiceCustom; // Importe o service
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    
    // Injeção do UserDetailsServiceCustom para carregar UserDetails
    private final UserDetailsServiceCustom userDetailsService; 

    public TokenDTO register(RegisterDTO dto) {
        User user = userMapper.toEntity(dto);

        // Aplica hash na senha
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        
        // Define ou mantém a Role
        if (dto.getRole() == null || dto.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        } else {
            user.setRole(dto.getRole());
        }

        userRepository.save(user);

        // >> CORREÇÃO: Carrega o UserDetails para a geração correta do Token (com roles)
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        
        var jwtToken = jwtService.generateToken(userDetails);
        
        return new TokenDTO(jwtToken, "Bearer");
    }

    public TokenDTO login(LoginDTO dto) {
        // Tenta autenticar
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        // >> CORREÇÃO: Carrega o UserDetails para a geração correta do Token (com roles)
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());

        var jwtToken = jwtService.generateToken(userDetails);
        
        return new TokenDTO(jwtToken, "Bearer");
    }
}