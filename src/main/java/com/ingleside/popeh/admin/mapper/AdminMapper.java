package com.ingleside.popeh.admin.mapper;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
  @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
  Admin mapToAdmin(AdminCreationsRequest adminCreationsRequest);

  AdminInformationResponse mapToAdminInformationResponse(Admin admin);
}
