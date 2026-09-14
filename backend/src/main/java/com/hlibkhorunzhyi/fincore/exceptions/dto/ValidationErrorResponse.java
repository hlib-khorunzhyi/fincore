package com.hlibkhorunzhyi.fincore.exceptions.dto;

import java.util.Map;

public record ValidationErrorResponse(

        Map<String, String> errors
) {
}
