package com.example.demo.domain.domain.post.service.write;

import com.example.demo.domain.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostWriteServiceImpl implements PostWriteService{

    private final PostRepository postRepository;

    @Override
    public void write(){

    }
}
