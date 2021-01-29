package com.covid.dashboard.data.repository;

import com.covid.dashboard.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
