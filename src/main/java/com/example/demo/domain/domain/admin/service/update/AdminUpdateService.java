package com.example.demo.domain.domain.admin.service.update;

import com.example.demo.domain.domain.admin.service.update.dto.AdminUpdateServiceDto;

public interface AdminUpdateService {

    void update(Long adminId, AdminUpdateServiceDto adminUpdateServiceDto);
}
