package com.example.demo.domain.domain.post.service.recommend.update;

import com.example.demo.exception.post.PostAlreadyRecommendedException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;

public interface PostRecommendUpdateService {

    /**
     * 해당 사용자가 추천하려는 게시물이 비추천 상태라면 추천상태로 변경하고 추천수가 증가합니다
     * 만약 이미 추천된 상태의 게시물이라면 익셉션이 발생합니다
     *
     * @param userId :사용자의 고유 아이디 값
     * @param postId :게시물의 고유 아이디 값
     * @throws UserNotFoundException           : 해당하는 유저를 찾을 수 없는 경우
     * @throws PostNotFoundException           : 해당하는 게시물을 찾을 수 없는 경우
     * @throws PostAlreadyRecommendedException : 해당게시물이 이미 추천상태인 경우
     **/
    void update(Long userId, Long postId);
}
