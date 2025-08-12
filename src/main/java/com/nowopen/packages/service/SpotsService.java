package com.nowopen.packages.service;


import com.nowopen.packages.dto.SpotsDto;
import com.nowopen.packages.entity.SpotBusinessHour;
import com.nowopen.packages.entity.Spots;
import com.nowopen.packages.repository.SpotsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotsService {

    final private SpotsRepository spotRepository;

    @Transactional
    public void saveSpot(SpotsDto req) {
        Spots spot = new Spots();
        spot.setName(req.getName());
        spot.setDescription(req.getDescription());
        spot.setLongitude(Double.valueOf(req.getLongitude()));
        spot.setLatitude(Double.valueOf(req.getLatitude()));
        spot.setPhotos(req.getPhotos());

        List<SpotBusinessHour> businessHours = new ArrayList<>();
        for (SpotsDto.BusinessHourDto sbh : req.getBusinessHours()) {
            businessHours.add(new SpotBusinessHour(
                    Integer.valueOf(sbh.getDayOfWeek()),
                    sbh.getOpenTime(),
                    sbh.getCloseTime()
            ));
        }
        spot.setBusinessHours(businessHours);
        spotRepository.save(spot);
    }
}
