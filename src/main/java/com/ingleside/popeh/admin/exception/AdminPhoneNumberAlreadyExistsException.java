package com.ingleside.popeh.admin.exception;

public class AdminPhoneNumberAlreadyExistsException extends RuntimeException {
    public AdminPhoneNumberAlreadyExistsException(){
        super("this phone number already token");
    }
}
