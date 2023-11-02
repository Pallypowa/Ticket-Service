package com.piko.ticketingservice.core.repository;

import com.piko.ticketingservice.core.model.UserBankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankCardRepository extends JpaRepository<UserBankCard, Long> {
    UserBankCard findByCardId(String cardId);
}
