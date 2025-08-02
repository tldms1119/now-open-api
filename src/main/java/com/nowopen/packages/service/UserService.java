package com.nowopen.packages.service;

import com.nowopen.packages.common.exception.ServiceException;
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

    public User signUp(String email, String password, String username) {
        String hashedPw = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPw(hashedPw);
        user.setUsername(username);
        user.setUserType("USER");
        return userRepository.save(user);
    }

    public User signIn(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        if (!passwordEncoder.matches(password, user.getPw())) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return user;
    }

}
