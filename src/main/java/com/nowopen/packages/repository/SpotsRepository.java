package com.nowopen.packages.repository;

import com.nowopen.packages.entity.Spots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotsRepository extends JpaRepository<Spots, Long> {
}
