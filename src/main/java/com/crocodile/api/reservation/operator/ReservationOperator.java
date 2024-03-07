package com.crocodile.api.reservation.operator;

import com.crocodile.api.common.exception.CrocodileIllegalArgumentException;
import com.crocodile.api.lesson.operator.LessonLimitDecreaseOperator;
import com.crocodile.api.reservation.controller.command.model.ReservationPostRequest;
import com.crocodile.api.reservation.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import javax.persistence.OptimisticLockException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationOperator {

    private final LessonLimitDecreaseOperator lessonLimitDecreaseOperator;

    public ReservationDto exe(ReservationPostRequest request) {
        try {
            return lessonLimitDecreaseOperator.exe(request);
        } catch (OptimisticLockingFailureException | OptimisticLockException | StaleObjectStateException e) {
            // 재시도
            return lessonLimitDecreaseOperator.exe(request);
        } catch (CrocodileIllegalArgumentException e) {
            throw CrocodileIllegalArgumentException.exception(e.getMessage());
        } catch (RuntimeException e) {
            log.error("예약 생성중 예외발생");
            throw e;
        }
    }

}
