package com.crocodile.api.lesson.provider;

import com.crocodile.api.lesson.domain.Lesson;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;

import javax.persistence.LockModeType;
import java.time.LocalDate;

import static com.crocodile.api.lesson.domain.QLesson.lesson;
import static com.crocodile.api.shop.domain.QShop.shop;

@Component
@RequiredArgsConstructor
public class LessonVarProvider {

    private final JPAQueryFactory jpaQueryFactory;

    @Lock(value = LockModeType.OPTIMISTIC)
    public Lesson get(
            long shopId,
            LocalDate date
    ) {
        return jpaQueryFactory
                .selectFrom(lesson)
                .join(lesson.shop, shop).fetchJoin()
                .where(
                        lesson.date.eq(date)
                                .and(shop.id.eq(shopId))
                ).fetchFirst();
    }

}
