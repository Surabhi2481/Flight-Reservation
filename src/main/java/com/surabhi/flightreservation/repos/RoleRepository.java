package com.surabhi.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surabhi.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
