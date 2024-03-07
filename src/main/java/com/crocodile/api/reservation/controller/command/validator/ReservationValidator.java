package com.crocodile.api.reservation.controller.command.validator;

import com.crocodile.api.common.exception.CrocodileIllegalArgumentException;
import com.crocodile.api.common.exception.CustomErrorCode;
import com.crocodile.api.common.utils.LocalDateUtils;
import com.crocodile.api.reservation.controller.command.model.ReservationPostRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Component
public class ReservationValidator {
    public void valid(Object request) {
        if (request instanceof ReservationPostRequest) {
            validShopId(((ReservationPostRequest) request).getShopId());
            validParentId(((ReservationPostRequest) request).getParentId());
            validCount(((ReservationPostRequest) request).getCount());
            validDate(((ReservationPostRequest) request).getDate());
        }
    }

    public void validShopId(Long shopId) {
        if (shopId == null) {
            throwException("shopId", "매장 식별값은 필수값 입니다.");
        }
    }

    public void validParentId(Long parentId) {
        if (parentId == null) {
            throwException("parentId", "부모 식별값은 필수값 입니다.");
        }
    }

    public void validCount(Integer count) {
        if (count == null) {
            throwException("count", "자녀수는 필수값 입니다.");
        }
    }

    public void validDate(String date) {
        if (!StringUtils.hasText(date)) {
            throwException("title", "제목은 필수값 입니다.");
        }
        try {
            LocalDateUtils.convertToLocalDate(date);
        } catch (Exception e) {
            throwException("date", "날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식이어야 합니다.");
        }
        LocalDate localDate = LocalDateUtils.convertToLocalDate(date);
        LocalDate now = LocalDate.now();
        if (localDate.isBefore(now)) {
            throwException("date", "과거 날짜로 예약 할 수 없습니다.");
        }
        if (now.equals(localDate)) {
            throwException("date", "당일 예약은 불가 합니다.");
        }
        if (localDate.isAfter(now.plusDays(14))) {
            throwException("date", "익일부터 14일 이후 까지 예약 가능합니다. 예시) 금일이 2/1 이면 2/15 일까지.");
        }
    }

    public void throwException(String field, String message) {
        throw CrocodileIllegalArgumentException.exception(
                field + " 가 올바르지 않습니다." + message,
                CustomErrorCode.BAD_REQUEST_BODY.name()
        );
    }
}
