package com.nowopen.packages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "spot_business_hours", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class SpotBusinessHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 0 = Sunday, 1 = Monday ... 6 = Saturday
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "open_time", nullable = false)
    private String openTime;

    @Column(name = "close_time", nullable = false)
    private String closeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", nullable = false)
    private Spot spot;

    public SpotBusinessHour(Integer dayOfWeek, String openTime, String closeTime) {
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

}
