package com.nowopen.packages.controller;

import com.nowopen.packages.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<BaseResponse<Object>> test(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.builder()
                        .result(true)
                        .message("Authentication checking")
                        .build());
    }

}
