package com.ttproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiError(int status, String message, Map<String, String> fieldErrors) {

    public ApiError(int status, String message) {
        this(status, message, Map.of());
    }
}
