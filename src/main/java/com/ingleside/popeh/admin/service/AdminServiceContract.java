package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminServiceContract {

    AdminInformationResponse create(AdminCreationsRequest adminCreationsRequest);
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(Long id);
    Admin updateAdmin(Admin updatedAdmin);
    void deleteAdmin(Long id);


}
