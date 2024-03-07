package com.crocodile.api.reservation.provider;

import com.crocodile.api.reservation.domain.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.crocodile.api.lesson.domain.QLesson.lesson;
import static com.crocodile.api.parent.domain.QParent.parent;
import static com.crocodile.api.reservation.domain.QReservation.reservation;
import static com.crocodile.api.shop.domain.QShop.shop;

@Component
@RequiredArgsConstructor
public class ReservationDuplicateProvider {

    private final JPAQueryFactory jpaQueryFactory;

    public Reservation get(
            Long shopId,
            LocalDate localDate,
            String email
    ) {
        return jpaQueryFactory
                .selectFrom(reservation)
                .join(reservation.lesson, lesson).fetchJoin()
                .join(reservation.parent, parent).fetchJoin()
                .join(lesson.shop, shop).fetchJoin()
                .where(
                        shop.id.eq(shopId)
                                .and(lesson.date.eq(localDate))
                                .and(parent.email.eq(email))
                ).distinct()
                .fetchOne();
    }
}
