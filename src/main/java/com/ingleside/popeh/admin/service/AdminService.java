package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import com.ingleside.popeh.admin.exception.AdminUsenameAlreadyExistsException;
import com.ingleside.popeh.admin.mapper.AdminMapper;
import com.ingleside.popeh.admin.repository.AdminRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

  @Override
  public List<Admin> getAllAdmins() {
    return adminRepository.findAll();
  }

  @Override
  public Optional<Admin> getAdminById(Long id) {
    return adminRepository.findById(id);
  }

  @Override
  public Admin updateAdmin(Admin updatedAdmin) {
    return adminRepository.save(updatedAdmin);
  }

  @Override
  public void deleteAdmin(Long id) {
    adminRepository.deleteById(id);

  }
}
