package com.crocodile.api.reservation.provider;

import com.crocodile.api.reservation.domain.Reservation;
import com.crocodile.api.reservation.domain.enumeration.ReservationStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.crocodile.api.lesson.domain.QLesson.lesson;
import static com.crocodile.api.reservation.domain.QReservation.reservation;
import static com.crocodile.api.shop.domain.QShop.shop;

@Component
@RequiredArgsConstructor
public class ReservationProvider {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Reservation> get(
            Long shopId,
            LocalDate localDate,
            ReservationStatus reservationStatus
    ) {
        return jpaQueryFactory
                .selectFrom(reservation)
                .join(reservation.lesson, lesson).fetchJoin()
                .join(lesson.shop, shop).fetchJoin()
                .where(
                        shop.id.eq(shopId)
                                .and(lesson.date.eq(localDate))
                                .and(buildPredicate(reservationStatus))
                ).distinct()
                .fetch();


    }

    private Predicate buildPredicate(ReservationStatus reservationStatus) {
        BooleanBuilder builder = new BooleanBuilder();
        if (reservationStatus != null) {
            builder.and(reservation.reservationStatus.eq(reservationStatus));
        }

        return builder.getValue();
    }
}
