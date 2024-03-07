package com.crocodile.api.reservation.operator;

import com.crocodile.api.common.exception.CrocodileIllegalArgumentException;
import com.crocodile.api.reservation.domain.Reservation;
import com.crocodile.api.reservation.domain.enumeration.ReservationStatus;
import com.crocodile.api.reservation.provider.ReservationByIdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class ReservationCancelOperator {

    private final ReservationByIdProvider reservationByIdProvider;

    public void exe(long reservationId) {
        // valid
        Reservation reservation = Optional.ofNullable(reservationByIdProvider.get(reservationId))
                .orElseThrow(() -> CrocodileIllegalArgumentException.exception("존재하지 않는 예약 데이터 입니다."));
        if (reservation.isCancel()) {
            throw CrocodileIllegalArgumentException.exception("이미 취소된 주문입니다.");
        }
        if (reservation.getLesson().isTodayOrBefore()) {
            throw CrocodileIllegalArgumentException.exception("당일 또는 과거 예약은 취소 할 수 없습니다.");
        }

        // exe
        reservation.updateReservationStatus(ReservationStatus.CANCEL);
        reservation.getLesson().increaseCurrentLimit(reservation.getCount());

    }
}
