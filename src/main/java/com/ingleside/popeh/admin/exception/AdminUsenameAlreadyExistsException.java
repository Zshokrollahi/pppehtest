package com.ingleside.popeh.admin.exception;

public class AdminUsenameAlreadyExistsException extends RuntimeException {

    public AdminUsenameAlreadyExistsException( ) {
        super("this user name already taken");
    }
}
