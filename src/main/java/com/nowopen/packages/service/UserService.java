package com.nowopen.packages.service;

import com.nowopen.packages.entity.User;
import com.nowopen.packages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    public boolean checkId(String id){
        User user = userRepository.findByUserId(id);
        return user != null;
    }

}
