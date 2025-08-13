package com.nowopen.packages.service;

import com.nowopen.packages.common.exception.ServiceException;
import com.nowopen.packages.dto.SpotListDto;
import com.nowopen.packages.dto.SpotsDto;
import com.nowopen.packages.entity.Spot;
import com.nowopen.packages.repository.SpotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpotsServiceTest {

    @Mock
    private SpotRepository spotRepository;

    @InjectMocks
    private SpotService spotService;

    @Test
    void testRegisterSpot() {
        List<SpotsDto.BusinessHourDto> businessHours = new ArrayList<>();
        businessHours.add(new SpotsDto.BusinessHourDto("0", "09:00", "17:00"));
        businessHours.add(new SpotsDto.BusinessHourDto("1", "10:00", "16:00"));
        businessHours.add(new SpotsDto.BusinessHourDto("2", "11:00", "15:00"));
        List<String> photos = List.of("photo1.jpg", "photo2.jpg");
        SpotsDto dto = SpotsDto.builder()
                .name("Test Spot")
                .description("Test Description")
                .latitude("37.7749")
                .longitude("-122.4194")
                .businessHours(businessHours)
                .photos(photos)
                .build();

        Spot spot = new Spot();
        dto.dtoToEntity(spot);

        when(spotRepository.save(any(Spot.class))).thenReturn(spot);
        assertDoesNotThrow(() -> spotService.registerSpot(dto));
        verify(spotRepository, times(1)).save(any(Spot.class));
    }

    @Test
    void testUpdateSpot() {
        Spot existingSpot = new Spot();
        existingSpot.setId(1L);

        SpotsDto dto = SpotsDto.builder()
                .id(1L)
                .name("Updated Spot")
                .description("Updated Description")
                .latitude("37.7749")
                .longitude("-122.4194")
                .build();

        when(spotRepository.findById(1L)).thenReturn(Optional.of(existingSpot));

        assertDoesNotThrow(() -> spotService.updateSpot(dto));
        verify(spotRepository, times(1)).findById(1L);
    }

    @Test
    void testGetList() {
        Spot spot = new Spot();
        spot.setId(1L);
        spot.setName("Test Spot");

        Page<Spot> page = new PageImpl<>(List.of(spot));
        when(spotRepository.findAll(any(PageRequest.class))).thenReturn(page);

        SpotListDto dto = new SpotListDto();
        dto.setPage(0);
        dto.setSize(10);

        List<SpotsDto> result = spotService.getList(dto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Spot", result.get(0).getName());
    }

    @Test
    void testGetNearbySpots() {
        Spot spot = new Spot();
        spot.setId(1L);
        spot.setName("Nearby Spot");

        when(spotRepository.findNearbySpots(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(List.of(spot));

        SpotListDto req = new SpotListDto();
        req.setLatitude(37.7749);
        req.setLongitude(-122.4194);
        req.setRadius(10.0);

        List<SpotsDto> result = spotService.getNearbySpots(req);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Nearby Spot", result.get(0).getName());
    }

    @Test
    void testDeleteSpot() {
        Spot spot = new Spot();
        spot.setId(1L);

        when(spotRepository.findById(1L)).thenReturn(Optional.of(spot));

        assertDoesNotThrow(() -> spotService.deleteSpot(1L));
        verify(spotRepository, times(1)).delete(spot);
    }

    @Test
    void testDeleteSpot_NotFound() {
        when(spotRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> spotService.deleteSpot(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Spot not found", exception.getMessage());
    }

}
