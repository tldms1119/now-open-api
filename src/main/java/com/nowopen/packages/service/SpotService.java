package com.nowopen.packages.service;


import com.nowopen.packages.common.exception.ServiceException;
import com.nowopen.packages.dto.SpotListDto;
import com.nowopen.packages.dto.SpotsDto;
import com.nowopen.packages.entity.SpotBusinessHour;
import com.nowopen.packages.entity.Spot;
import com.nowopen.packages.repository.SpotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotService {

    final private SpotRepository spotRepository;
    final private UserService userService;

    @Transactional
    public void registerSpot(SpotsDto req) {
        Spot spot = new Spot();
        req.dtoToEntity(spot);
        if(req.getBusinessHours() != null){
            List<SpotBusinessHour> businessHours = new ArrayList<>();
            for (SpotsDto.BusinessHourDto sbh : req.getBusinessHours()) {
                SpotBusinessHour businessHour = new SpotBusinessHour(
                        Integer.valueOf(sbh.getDayOfWeek()),
                        sbh.getOpenTime(),
                        sbh.getCloseTime()
                );
                businessHour.setSpot(spot);
                businessHours.add(businessHour);
            }
            spot.setBusinessHours(businessHours);
        }
        spot.setUser(userService.getCurrentUser());
        spotRepository.save(spot);
    }

    @Transactional
    public void updateSpot(SpotsDto req) {
        Spot existingSpot = spotRepository.findById(req.getId()).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Spot not found"));
        req.dtoToEntity(existingSpot);

        if (req.getBusinessHours() != null) {
            // Clear the existing business hours
            existingSpot.getBusinessHours().clear();
            for (SpotsDto.BusinessHourDto sbh : req.getBusinessHours()) {
                SpotBusinessHour businessHour = new SpotBusinessHour(
                        Integer.valueOf(sbh.getDayOfWeek()),
                        sbh.getOpenTime(),
                        sbh.getCloseTime()
                );
                businessHour.setSpot(existingSpot);
                existingSpot.getBusinessHours().add(businessHour);
            }
        }
    }

    public List<SpotsDto> getList(SpotListDto dto) {
        List<Spot> spots = spotRepository.findAll(PageRequest.of(dto.getPage(), dto.getSize())).getContent();
        return mapToSpotsDtoList(spots);
    }

    public List<SpotsDto> getNearbySpots(SpotListDto req) {
        List<Spot> spots = spotRepository.findNearbySpots(req.getLatitude(), req.getLongitude(), req.getRadius());
        return mapToSpotsDtoList(spots);
    }

    private List<SpotsDto> mapToSpotsDtoList(List<Spot> spots) {
        return spots.stream()
                .map(spot -> SpotsDto.builder()
                        .id(spot.getId())
                        .name(spot.getName())
                        .description(spot.getDescription())
                        .latitude(String.valueOf(spot.getLatitude()))
                        .longitude(String.valueOf(spot.getLongitude()))
                        .photos(spot.getPhotos())
                        .businessHours(spot.getBusinessHours() != null ? spot.getBusinessHours().stream()
                                .map(bh -> new SpotsDto.BusinessHourDto(
                                        String.valueOf(bh.getDayOfWeek()),
                                        bh.getOpenTime(),
                                        bh.getCloseTime()))
                                .toList() : null)
                        .build())
                .toList();
    }

    @Transactional
    public void deleteSpot(Long id) {
        Spot spot = spotRepository.findById(id).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Spot not found"));
        spotRepository.delete(spot);
    }
}
