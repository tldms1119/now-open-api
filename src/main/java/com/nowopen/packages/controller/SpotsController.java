package com.nowopen.packages.controller;

import com.nowopen.packages.dto.BaseResponse;
import com.nowopen.packages.dto.SpotsDto;
import com.nowopen.packages.service.SpotsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/spots")
@RestController
@RequiredArgsConstructor
public class SpotsController {

    final private SpotsService spotsService;
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<SpotsDto>> createSpot(@RequestBody SpotsDto req) {
        spotsService.saveSpot(req);
        return ResponseEntity
                .ok(BaseResponse.<SpotsDto>builder()
                        .result(true)
                        .message("Spot created successfully")
                        .payload(req)
                        .build());
    }
}
