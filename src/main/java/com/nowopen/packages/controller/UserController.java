package com.nowopen.packages.controller;

import com.nowopen.packages.common.config.AppConfig;
import com.nowopen.packages.service.UserService;
import com.nowopen.packages.vo.BaseResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    final AppConfig appConfig;
    final UserService userService;

    @GetMapping("/check-id")
    public ResponseEntity<BaseResponseVO> checkId(String id){
        log.info("Check ID duplication");
        boolean isExist = userService.checkId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponseVO.builder()
                        .result(!isExist)
                        .message(isExist ? "Already existed ID" : "This ID is available")
                        .build());
    }

}
