package com.piko.ticketingservice.core.repository;

import com.piko.ticketingservice.core.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
