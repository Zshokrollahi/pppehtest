package com.ingleside.popeh.admin.dto;

public record AdminCreationsRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        String phoneNumber
) {
}
