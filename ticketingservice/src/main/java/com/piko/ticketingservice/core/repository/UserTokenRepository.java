package com.piko.ticketingservice.core.repository;

import com.piko.ticketingservice.core.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}
