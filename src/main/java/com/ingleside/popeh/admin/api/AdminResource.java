package com.ingleside.popeh.admin.api;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.exception.AdminUsenameAlreadyExistsException;
import com.ingleside.popeh.admin.service.AdminServiceContract;
import com.ingleside.popeh.error.dto.ExceptionResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class AdminResource {
    private final AdminServiceContract adminServiceContract;
    @PostMapping("/admins")
    public AdminInformationResponse create(@RequestBody AdminCreationsRequest adminCreationsRequest) {
        return adminServiceContract.create(adminCreationsRequest);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AdminUsenameAlreadyExistsException.class)
    public ExceptionResponse handle(AdminUsenameAlreadyExistsException e){
        return new ExceptionResponse( HttpStatus.CONFLICT.value() ,
                HttpStatus.CONFLICT, e.getMessage() ,
                LocalDateTime.now());

    }

}
