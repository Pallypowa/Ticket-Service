package com.piko.ticketingservice.core.repository;

import com.piko.ticketingservice.core.model.UserBankCard;
import com.piko.ticketingservice.core.model.UserDevice;
import com.piko.ticketingservice.core.model.UserToken;
import com.piko.ticketingservice.core.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryManager {
    private final UserBankCardRepository userBankCardRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    public RepositoryManager(UserBankCardRepository userBankCardRepository, UserDeviceRepository userDeviceRepository, UserRepository userRepository, UserTokenRepository userTokenRepository) {
        this.userBankCardRepository = userBankCardRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
    }

    public List<Users> getUsers() {
        return this.userRepository.findAll();
    }

    public List<UserDevice> getUserDevices() {
        return this.userDeviceRepository.findAll();
    }

    public List<UserToken> getUserTokens() {
        return this.userTokenRepository.findAll();
    }

    public List<UserBankCard> getUserBankCards() {
        return this.userBankCardRepository.findAll();
    }

    public Users getUser(Long userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }

    public List<UserDevice> getDeviceHashesByUser(Long userId) {
        return userDeviceRepository.findAllByUsersId(userId);
    }

    public UserBankCard getUserBankCardsForUser(Long userId) {
        return userBankCardRepository.findById(userId).orElseThrow();
    }

    public UserBankCard getUserBankCardByCardId(String cardId) {
        return userBankCardRepository.findByCardId(cardId);
    }

}
