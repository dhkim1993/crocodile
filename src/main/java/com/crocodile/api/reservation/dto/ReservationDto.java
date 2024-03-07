package com.crocodile.api.reservation.dto;

import com.crocodile.api.reservation.domain.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long reservationId;
    private Long shopId;
    private String shopName;
    private Long lessonId;
    private String lessonName;
    private LocalDate date;
    private String reservationStatus;
    private Long parentId;
    private String parentName;
    private String email;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;

    public static ReservationDto toDto(Reservation reservation) {
        return ReservationDto.builder()
                .reservationId(reservation.getId())
                .shopId(reservation.getLesson().getShop().getId())
                .shopName(reservation.getLesson().getShop().getShopName())
                .lessonId(reservation.getLesson().getId())
                .lessonName(reservation.getLesson().getLessonName())
                .date(reservation.getLesson().getDate())
                .reservationStatus(reservation.getReservationStatus().getValue())
                .parentId(reservation.getParent().getId())
                .parentName(reservation.getParent().getParentName())
                .email(reservation.getParent().getEmail())
                .count(reservation.getCount())
                .createdDateTime(reservation.getCreatedDateTime())
                .updatedDateTime(reservation.getUpdatedDateTime())
                .build();
    }

}
