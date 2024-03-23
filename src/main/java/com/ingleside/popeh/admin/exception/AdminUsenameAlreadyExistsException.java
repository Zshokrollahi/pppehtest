package com.ingleside.popeh.admin.exception;

public class AdminUsenameAlreadyExistsException extends RuntimeException {

    public AdminUsenameAlreadyExistsException() {
        super("admin with this username already exists ");
    }
}
