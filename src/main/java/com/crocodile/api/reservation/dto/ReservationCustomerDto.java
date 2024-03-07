package com.crocodile.api.reservation.dto;

import com.crocodile.api.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCustomerDto {
    private Long parentId;
    private String parentName;
    private String email;
    private Integer count;

    public static ReservationCustomerDto toDto(Reservation entity) {
        return ReservationCustomerDto.builder()
                .parentId(entity.getParent().getId())
                .parentName(entity.getParent().getParentName())
                .email(entity.getParent().getEmail())
                .count(entity.getCount())
                .build();
    }

}
