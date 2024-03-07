package com.crocodile.api.lesson.operator;

import com.crocodile.api.common.exception.CrocodileIllegalArgumentException;
import com.crocodile.api.common.utils.LocalDateUtils;
import com.crocodile.api.lesson.domain.Lesson;
import com.crocodile.api.lesson.provider.LessonVarProvider;
import com.crocodile.api.parent.domain.Parent;
import com.crocodile.api.parent.repository.ParentRepository;
import com.crocodile.api.reservation.controller.command.model.ReservationPostRequest;
import com.crocodile.api.reservation.domain.Reservation;
import com.crocodile.api.reservation.domain.enumeration.ReservationStatus;
import com.crocodile.api.reservation.dto.ReservationDto;
import com.crocodile.api.reservation.provider.ReservationDuplicateProvider;
import com.crocodile.api.reservation.repository.ReservationRepository;
import com.crocodile.api.shop.domain.Shop;
import com.crocodile.api.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class LessonLimitDecreaseOperator {

    private final ParentRepository parentRepository;
    private final ReservationRepository reservationRepository;
    private final ShopRepository shopRepository;
    private final LessonVarProvider lessonVarProvider;
    private final ReservationDuplicateProvider reservationDuplicateProvider;

    public ReservationDto exe(
            ReservationPostRequest request
    ) {
        // valid
        LocalDate localDate = LocalDateUtils.convertToLocalDate(request.getDate());
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> CrocodileIllegalArgumentException.exception("존재하지 않는 매장 데이터 입니다."));
        Lesson lesson = Optional.ofNullable(lessonVarProvider.get(shop.getId(), localDate))
                .orElseThrow(() -> CrocodileIllegalArgumentException.exception("존재하지 않는 수업 데이터 입니다."));
        Parent parent = parentRepository.findById(request.getParentId())
                .orElseThrow(() -> CrocodileIllegalArgumentException.exception("존재하지 않는 부모 데이터 입니다."));
        // 같은 날짜에 동일한 부모가 예약했는지 체크
        Reservation reservation = reservationDuplicateProvider.get(
                shop.getId(),
                localDate,
                parent.getEmail()
        );
        if (reservation != null) {
            throw CrocodileIllegalArgumentException.exception("이미 해당날짜에 해당 수업 예약 데이터가 존재 합니다.");
        }
        lesson.decreaseCurrentLimit(request.getCount());

        // 예약 생성
        Reservation saveReservation = reservationRepository.save(
                Reservation.builder()
                        .lesson(lesson)
                        .parent(parent)
                        .count(request.getCount())
                        .reservationStatus(ReservationStatus.COMPLETE)
                        .build()
        );
        return ReservationDto.toDto(saveReservation);
    }
}
