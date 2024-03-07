package com.crocodile.api.reservation.controller.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPostRequest {
    private Long shopId;
    private Long parentId;
    private Integer count;
    private String date;
}
