package com.crocodile.api.reservation.controller.query;

import com.crocodile.api.common.rest.RestResponse;
import com.crocodile.api.common.utils.LocalDateUtils;
import com.crocodile.api.reservation.service.ReservationHistoryService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation/histories")
@RequiredArgsConstructor
@Api(tags = "예약이력 조회 API")
public class ReservationHistoriesGet {

    private final ReservationHistoryService reservationHistoryService;

    @Operation(description = "예약이력을 조회합니다. 매장 식별값, 날짜로 조회 할 수 있습니다.")
    @GetMapping
    public ResponseEntity get(
            @RequestParam Long shopId,
            @RequestParam String date
    ) {
        return new ResponseEntity(
                new RestResponse(
                        reservationHistoryService.get(
                                shopId,
                                LocalDateUtils.convertToLocalDate(date)
                        ),
                        null
                ),
                HttpStatus.OK
        );
    }

}
