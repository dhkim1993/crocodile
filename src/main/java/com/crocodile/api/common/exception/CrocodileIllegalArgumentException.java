package com.crocodile.api.common.exception;

import lombok.Getter;

@Getter
public class CrocodileIllegalArgumentException extends IllegalArgumentException {

    private final String code;

    public boolean logging = false;

    private CrocodileIllegalArgumentException(String message, Exception ex, String code, boolean logging) {
        super(message, ex);
        this.code = code;
        this.logging = logging;
    }

    public static CrocodileIllegalArgumentException exception(String message) {
        return exception(message, null, false);
    }

    public static CrocodileIllegalArgumentException exception(String message, String code) {
        return exception(message, null, code, false);
    }

    public static CrocodileIllegalArgumentException exception(String message, Exception e, boolean logging) {
        return new CrocodileIllegalArgumentException(message, e, null, logging);
    }

    public static CrocodileIllegalArgumentException exception(String message, Exception e, String code, boolean logging) {
        return new CrocodileIllegalArgumentException(message, e, code, logging);
    }

}
