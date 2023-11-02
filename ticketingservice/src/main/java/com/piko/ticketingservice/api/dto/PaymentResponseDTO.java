package com.piko.ticketingservice.api.dto;

public class PaymentResponseDTO {
    private Boolean success;
    private Long reserver;

    public PaymentResponseDTO(Boolean success, Long reserver) {
        this.success = success;
        this.reserver = reserver;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getReserver() {
        return reserver;
    }

    public void setReserver(Long reserver) {
        this.reserver = reserver;
    }
}
