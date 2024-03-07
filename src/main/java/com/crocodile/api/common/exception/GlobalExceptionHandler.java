package com.crocodile.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ex.printStackTrace();

        try {
            Map<String, String> errorsMap = new HashMap<>();
            errorsMap.put("stackTrace", ExceptionUtils.getStackTrace(ex));
            if (ServletWebRequest.class.isInstance(request)) {
                ServletWebRequest servletWebRequest = ServletWebRequest.class.cast(request);
                if (servletWebRequest != null) {
                    HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
                    if (httpServletRequest != null) {
                        if (httpServletRequest.getRequestURL() != null) {
                            String requestURL = httpServletRequest.getRequestURL().toString();
                            if (StringUtils.isNotEmpty(requestURL)) {
                                errorsMap.put("request", requestURL);
                            }
                        }
                        String queryString = httpServletRequest.getQueryString();
                        if (StringUtils.isNotEmpty(queryString)) {
                            errorsMap.put("requestQueryString", queryString);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return new ResponseEntity(
                new ExceptionResponse(
                        null,
                        new ExceptionResponse.Error(
                                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                ex.getMessage()
                        )
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }

    @ExceptionHandler({CrocodileIllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(CrocodileIllegalArgumentException ex) {
        return new ResponseEntity(
                new ExceptionResponse(
                        null,
                        new ExceptionResponse.Error(
                                ex.getCode(),
                                ex.getMessage()
                        )
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExceptionResponse {
        private Object data;
        private Error error;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Error {
            private String code;
            private String message;
        }
    }

}
