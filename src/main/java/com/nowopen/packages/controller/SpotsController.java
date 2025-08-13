package com.nowopen.packages.controller;

import com.nowopen.packages.dto.BaseResponse;
import com.nowopen.packages.dto.SpotListDto;
import com.nowopen.packages.dto.SpotsDto;
import com.nowopen.packages.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/spots")
@RestController
@RequiredArgsConstructor
public class SpotsController {

    final private SpotService spotService;
    final private static String DEFAULT_TYPE = "MAP";

    @GetMapping
    public ResponseEntity<BaseResponse<SpotListDto>> getSpots(SpotListDto req) {
        List<SpotsDto> spots = DEFAULT_TYPE.equals(req.getType()) ? spotService.getNearbySpots(req) : spotService.getList(req);
        req.setList(spots);
        return ResponseEntity
                .ok(BaseResponse.<SpotListDto>builder()
                        .result(true)
                        .message("Spot list retrieved successfully")
                        .payload(req)
                        .build());
    }

    @PostMapping
    public ResponseEntity<BaseResponse<SpotsDto>> register(@RequestBody SpotsDto req) {
        spotService.registerSpot(req);
        return ResponseEntity
                .ok(BaseResponse.<SpotsDto>builder()
                        .result(true)
                        .message("Spot created successfully")
                        .payload(req)
                        .build());
    }

    @PutMapping
    public ResponseEntity<BaseResponse<SpotsDto>> update(@RequestBody SpotsDto req) {
        spotService.updateSpot(req);
        return ResponseEntity
                .ok(BaseResponse.<SpotsDto>builder()
                        .result(true)
                        .message("Spot updated successfully")
                        .payload(req)
                        .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        spotService.deleteSpot(id);
        return ResponseEntity
                .ok(BaseResponse.<Void>builder()
                        .result(true)
                        .message("Spot deleted successfully")
                        .build());
    }
}
