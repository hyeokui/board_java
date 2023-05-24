package com.example.demo.domain.domain.post.service.user.delete;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostDeleteServiceImpl implements UserPostDeleteService {

    private final PostRepository postRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void deleteByUser(){

    }
}
