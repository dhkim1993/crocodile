package com.crocodile.api.reservation.domain.enumeration;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    COMPLETE("완료"),
    CANCEL("취소");

    private final String value;

    ReservationStatus(String value) {
        this.value = value;
    }
}
