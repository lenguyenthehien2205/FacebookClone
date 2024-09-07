package com.project.facebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.facebook.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
