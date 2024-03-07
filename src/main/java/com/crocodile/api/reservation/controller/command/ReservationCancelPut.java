package com.crocodile.api.reservation.controller.command;

import com.crocodile.api.common.rest.RestResponse;
import com.crocodile.api.reservation.operator.ReservationCancelOperator;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation/{reservationId}/cancel")
@RequiredArgsConstructor
@Api(tags = "예약 취소 API")
public class ReservationCancelPut {

    private final ReservationCancelOperator reservationCancelOperator;

    @Operation(description = "예약을 취소합니다.")
    @PutMapping
    public ResponseEntity update(
            @PathVariable Long reservationId
    ) {
        reservationCancelOperator.exe(reservationId);
        return new ResponseEntity(
                new RestResponse(
                        null,
                        null
                ),
                HttpStatus.OK
        );
    }
}
