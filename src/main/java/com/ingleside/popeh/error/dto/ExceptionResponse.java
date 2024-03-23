package com.ingleside.popeh.error.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponse(int statusCode , HttpStatus status , String massage , LocalDateTime issueAt) {
}
