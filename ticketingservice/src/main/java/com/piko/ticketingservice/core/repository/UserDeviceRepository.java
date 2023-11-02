package com.piko.ticketingservice.core.repository;

import com.piko.ticketingservice.core.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findAllByUsersId(Long userId);
}
