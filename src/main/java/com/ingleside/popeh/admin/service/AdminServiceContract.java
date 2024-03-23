package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;

public interface AdminServiceContract {

    AdminInformationResponse create(AdminCreationsRequest adminCreationsRequest);
}
