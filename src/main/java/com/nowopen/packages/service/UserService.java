package com.nowopen.packages.service;

import com.nowopen.packages.dto.UserDto;
import com.nowopen.packages.entity.User;
import com.nowopen.packages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;

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



}
