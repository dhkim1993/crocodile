package com.crocodile.api.reservation.service;

import com.crocodile.api.reservation.domain.enumeration.ReservationStatus;
import com.crocodile.api.reservation.dto.ReservationCustomerDto;
import com.crocodile.api.reservation.provider.ReservationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationCustomerService {

    private final ReservationProvider reservationProvider;

    public List<ReservationCustomerDto> get(
            Long shopId,
            LocalDate localDate
    ) {
        return reservationProvider.get(
                        shopId,
                        localDate,
                        ReservationStatus.COMPLETE
                ).stream()
                .map(ReservationCustomerDto::toDto)
                .collect(Collectors.toList());
    }
}
