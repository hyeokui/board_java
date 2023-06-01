package com.example.demo.domain.domain.post.service.recommend.add;

import com.example.demo.exception.post.PostAlreadyRecommendedException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;

public interface PostRecommendAddService {

    /**
     * 추천하려는 게시물의 해당 사용자의 중복추천여부를 검사합니다
     * 이미 추천한 게시물일시 익셉션발생 추천하지 않은 게시물일 경우
     * 해당 사용자와 게시물의 고유 id를 가진 게시물 추천 객체가 생성되며 게시물의 추천수가 증가합니다
     *
     * @param userId :사용자의 고유 아이디 값
     * @param postId :게시물의 고유 아이디 값
     * @throws UserNotFoundException:           해당하는 유저를 찾을 수 없는 경우
     * @throws PostNotFoundException:           해당하는 게시물을 찾을 수 없는 경우
     * @throws PostAlreadyRecommendedException: 해당게시물이 이미 추천상태인 경우
     **/
    void add(Long postId, Long userId);
}
