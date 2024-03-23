package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import com.ingleside.popeh.admin.exception.AdminPhoneNumberAlreadyExistsException;
import com.ingleside.popeh.admin.exception.AdminUsenameAlreadyExistsException;
import com.ingleside.popeh.admin.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdminService implements AdminServiceContract {

    private final AdminRepository adminRepository;

    @Override
    public AdminInformationResponse create(AdminCreationsRequest adminCreationsRequest) {
        if (adminRepository.existsByUsername(adminCreationsRequest.username()))
            throw new AdminUsenameAlreadyExistsException();

        if (adminRepository.existsByPhoneNumber(adminCreationsRequest.phoneNumber()))
            throw new AdminPhoneNumberAlreadyExistsException();
        var savedAdmin = adminRepository.save(Admin.builder()
                .firstName(adminCreationsRequest.firstName())
                .lastName(adminCreationsRequest.lastName())
                .password(adminCreationsRequest.password())
                .username(adminCreationsRequest.username())
                .phoneNumber(adminCreationsRequest.phoneNumber())
                .createdAt(LocalDateTime.now())
                .build());


        return new AdminInformationResponse(savedAdmin.getId(),
                savedAdmin.getFirstName(),
                savedAdmin.getLastName(),
                savedAdmin.getUsername(),
                savedAdmin.getPassword(),
                savedAdmin.getPhoneNumber(),
                savedAdmin.getCreatedAt());
    }


}
