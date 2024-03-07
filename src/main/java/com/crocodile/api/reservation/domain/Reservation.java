package com.crocodile.api.reservation.domain;

import com.crocodile.api.common.domain.BaseTimeEntity;
import com.crocodile.api.lesson.domain.Lesson;
import com.crocodile.api.parent.domain.Parent;
import com.crocodile.api.reservation.domain.enumeration.ReservationStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column
    private Integer count;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public void updateReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public boolean isCancel() {
        return ReservationStatus.CANCEL.equals(this.reservationStatus);
    }


}
