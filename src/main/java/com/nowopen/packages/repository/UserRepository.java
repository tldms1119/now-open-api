package com.nowopen.packages.repository;

import com.nowopen.packages.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String id);

}
