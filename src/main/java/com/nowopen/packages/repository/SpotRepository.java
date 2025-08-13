package com.nowopen.packages.repository;

import com.nowopen.packages.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    @Query("SELECT s FROM Spot s WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * " +
            "cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude)))) < :radius")
    List<Spot> findNearbySpots(@Param("latitude") double latitude,
                               @Param("longitude") double longitude,
                               @Param("radius") double radius);
}
