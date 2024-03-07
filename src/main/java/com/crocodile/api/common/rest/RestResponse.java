package com.crocodile.api.common.rest;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse {
    private Object data;
    private Error error;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }
}
