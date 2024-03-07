package com.crocodile.api.lesson.domain;

import com.crocodile.api.common.domain.BaseTimeEntity;
import com.crocodile.api.reservation.domain.enumeration.ReservationStatus;
import com.crocodile.api.shop.domain.Shop;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Lesson extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(nullable = false)
    private String LessonName;

    @Column(nullable = false, columnDefinition = "default 20")
    private Integer lessonLimit;

    @Column(nullable = false, columnDefinition = "default 20")
    private Integer currentLimit;

    @Version
    private Long version;

    @Column(nullable = false)
    private LocalDate date;

    public void decreaseCurrentLimit(int requestCount) {
        if (this.currentLimit - requestCount >= 0) {
            this.currentLimit -= requestCount;
        } else {
            throw new IllegalStateException("현재 예약 가능한 인원은 " + this.getCurrentLimit() + " 명 입니다.");
        }
    }

    public void increaseCurrentLimit(int requestCount) {
        if (this.currentLimit + requestCount <= this.lessonLimit) {
            this.currentLimit += requestCount;
        } else {
            throw new IllegalStateException("예약 취소중 문제가 발생하였습니다. 고겍센터에 문의 바랍니다.");
        }
    }

    public boolean isTodayOrBefore() {
        LocalDate now = LocalDate.now();
        return this.date.isEqual(now) || this.date.isBefore(now);
    }

}
