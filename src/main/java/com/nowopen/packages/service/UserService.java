package com.nowopen.packages.service;

import com.nowopen.packages.common.exception.ServiceException;
import com.nowopen.packages.dto.UserDto;
import com.nowopen.packages.entity.User;
import com.nowopen.packages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

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
        return UserDto.builder().email(user.getEmail()).username(user.getUsername()).build();
    }

    public UserDto signIn(UserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ServiceException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPw())) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return UserDto.builder().email(user.getEmail()).username(user.getUsername()).build();
    }

}
