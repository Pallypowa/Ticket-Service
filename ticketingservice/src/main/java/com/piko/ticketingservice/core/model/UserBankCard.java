package com.piko.ticketingservice.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserBankCard {
    @Id
    @GeneratedValue
    private Long userId;

    private String cardId;
    private String cardNumber;
    private Integer cvc;
    private String name;
    private Integer amount;
    private String currency; //We could also use enum...

    public UserBankCard() {
    }

    public UserBankCard(Long userId, String cardId, String cardNumber, Integer cvc, String name, Integer amount, String currency) {
        this.userId = userId;
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.name = name;
        this.amount = amount;
        this.currency = currency;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }
}
