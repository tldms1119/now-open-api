package com.nowopen.packages.service;

import com.nowopen.packages.common.exception.ServiceException;
import com.nowopen.packages.dto.UserDto;
import com.nowopen.packages.entity.User;
import com.nowopen.packages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final JwtService jwtService;

    public boolean checkEmailDuplicated(String email){
        return userRepository.existsByEmail(email);
    }

    public UserDto signUp(UserDto dto) {
        String hashedPw = passwordEncoder.encode(dto.getPassword());
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPw(hashedPw);
        user.setUsername(dto.getUsername());
        user.setUserType("USER");
        userRepository.save(user);
        return UserDto.builder().email(user.getEmail()).username(user.getRealUsername()).build();
    }

    public UserDto signIn(UserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ServiceException(HttpStatus.UNAUTHORIZED));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), user.getAuthorities()));
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getRealUsername())
                .token(jwtService.generateToken(user))
                .expireIn(jwtService.getExpirationTime())
                .build();
    }

}
