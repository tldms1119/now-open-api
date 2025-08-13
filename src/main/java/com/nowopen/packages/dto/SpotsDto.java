package com.nowopen.packages.dto;

import com.nowopen.packages.entity.Spot;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SpotsDto {

    private Long id;
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private List<String> photos;
    private List<BusinessHourDto> businessHours;

    @Getter
    @Setter
    public static class BusinessHourDto {
        private String dayOfWeek; // 0 = Sunday, 1 = Monday ... 6 = Saturday
        private String openTime; // Format: HH:mm
        private String closeTime; // Format: HH:mm

        public BusinessHourDto(String dayOfWeek, String openTime, String closeTime) {
            this.dayOfWeek = dayOfWeek;
            this.openTime = openTime;
            this.closeTime = closeTime;
        }
    }

    public void dtoToEntity(Spot spot) {
        spot.setName(this.getName());
        spot.setDescription(this.getDescription());
        spot.setLongitude(Double.valueOf(this.getLongitude()));
        spot.setLatitude(Double.valueOf(this.getLatitude()));
        spot.setPhotos(this.getPhotos());
    }
}
