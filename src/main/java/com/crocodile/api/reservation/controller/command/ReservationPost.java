package com.crocodile.api.reservation.controller.command;

import com.crocodile.api.common.rest.RestResponse;
import com.crocodile.api.reservation.controller.command.validator.ReservationValidator;
import com.crocodile.api.reservation.operator.ReservationOperator;
import com.crocodile.api.reservation.controller.command.model.ReservationPostRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@Api(tags = "예약 생성 API")
public class ReservationPost {

    private final ReservationOperator reservationOperator;
    private final ReservationValidator reservationValidator;

    @Operation(description = "예약을 생성합니다. 매장 식별값, 부모 식별값, 자녀수, 날짜가 필요합니다.")
    @PostMapping
    public ResponseEntity create(
            @RequestBody ReservationPostRequest request
    ) {
        reservationValidator.valid(request);
        return new ResponseEntity(
                new RestResponse(
                        reservationOperator.exe(request),
                        null
                ),
                HttpStatus.CREATED
        );
    }
}
