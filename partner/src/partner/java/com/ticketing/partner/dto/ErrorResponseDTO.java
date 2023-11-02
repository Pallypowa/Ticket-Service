package com.ticketing.partner.dto;

public class ErrorResponseDTO {
    private Boolean success;
    private Integer errorCode;

    public ErrorResponseDTO(Boolean success, Integer errorCode) {
        this.success = success;
        this.errorCode = errorCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
