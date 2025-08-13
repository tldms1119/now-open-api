package com.nowopen.packages.controller;

import com.nowopen.packages.common.config.AppConfig;
import com.nowopen.packages.dto.BaseResponse;
import com.nowopen.packages.dto.UserDto;
import com.nowopen.packages.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    final AppConfig appConfig;
    final UserService userService;

    @GetMapping("/check-email")
    public ResponseEntity<BaseResponse<Object>> checkEmailDuplicated(String email){
        boolean isExist = userService.checkEmailDuplicated(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.builder()
                        .result(!isExist)
                        .message(isExist ? "Email already exists" : "This email is available")
                        .build());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<UserDto>> signUp(@RequestBody UserDto req){
        UserDto dto = userService.signUp(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .result(true)
                        .payload(dto)
                        .build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponse<UserDto>> signIp(@RequestBody UserDto req){
        UserDto dto = userService.signIn(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .result(true)
                        .payload(dto)
                        .build());
    }

}
