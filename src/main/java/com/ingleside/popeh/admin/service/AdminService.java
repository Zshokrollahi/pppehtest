package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import com.ingleside.popeh.admin.exception.AdminUsenameAlreadyExistsException;
import com.ingleside.popeh.admin.mapper.AdminMapper;
import com.ingleside.popeh.admin.repository.AdminRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService implements AdminServiceContract {

  private final AdminRepository adminRepository;
  private final AdminMapper adminMapper;

  @Override
  public AdminInformationResponse create(AdminCreationsRequest adminCreationsRequest) {
    if (adminRepository.existsByUsername(adminCreationsRequest.username()))
      throw new AdminUsenameAlreadyExistsException();

    var savableAdmin = adminMapper.mapToAdmin(adminCreationsRequest);

    var savedAdmin = adminRepository.save(savableAdmin);

    return adminMapper.mapToAdminInformationResponse(savedAdmin);
  }
}
