package com.ingleside.popeh.admin.dto;

import java.time.LocalDateTime;

public record AdminInformationResponse(
        Long id ,
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber,
        LocalDateTime createdAt
) {
}
