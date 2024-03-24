package com.ingleside.popeh.admin.dto;

import lombok.Builder;

@Builder
public record AdminCreationsRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber
) {
}
