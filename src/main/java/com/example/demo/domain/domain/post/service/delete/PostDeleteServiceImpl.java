package com.example.demo.domain.domain.post.service.delete;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDeleteServiceImpl implements PostDeleteService{

    private final PostRepository postRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void deleteByAdmin(){

    }
}
