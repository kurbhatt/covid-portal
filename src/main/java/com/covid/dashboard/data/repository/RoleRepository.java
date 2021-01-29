package com.covid.dashboard.data.repository;

import com.covid.dashboard.data.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
