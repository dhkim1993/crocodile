package com.crocodile.api.common.utils;

import com.crocodile.api.common.exception.CrocodileIllegalArgumentException;
import com.crocodile.api.common.exception.CustomErrorCode;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class LocalDateUtils {

    public static LocalDate convertToLocalDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw CrocodileIllegalArgumentException.exception(
                    "날짜 타입이 올바르지 않습니다.yyyy-MM-dd 타입 입니다.",
                    CustomErrorCode.BAD_REQUEST_DATE_TYPE.name()
            );
        }

    }
}
