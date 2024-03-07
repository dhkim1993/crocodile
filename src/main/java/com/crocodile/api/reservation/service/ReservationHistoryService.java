package com.crocodile.api.reservation.service;

import com.crocodile.api.reservation.dto.ReservationDto;
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
public class ReservationHistoryService {

    private final ReservationProvider reservationProvider;

    public List<ReservationDto> get(Long shopId, LocalDate localDate) {
        return reservationProvider.get(
                        shopId,
                        localDate,
                        null
                ).stream()
                .map(ReservationDto::toDto)
                .collect(Collectors.toList());
    }
}
