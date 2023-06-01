package com.example.demo.domain.domain.post.service.recommend.cancel;

import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.post.PostNotRecommendedException;
import com.example.demo.exception.user.UserNotFoundException;

public interface PostRecommendCancelService {

    /**
     * 해당 사용자가 추천하려는 게시물이 추천 상태라면 비추천상태로 변경하고 추천수가 감소합니다
     * 이미 비추천 상태의 게시물이라면 해당하는 익셉션이 발생합니다
     *
     * @param userId :사용자의 고유 아이디 값
     * @param postId :게시물의 고유 아이디 값
     * @throws UserNotFoundException        : 해당하는 유저를 찾을 수 없는 경우
     * @throws PostNotFoundException        : 해당하는 게시물을 찾을 수 없는 경우
     * @throws PostNotRecommendedException: 추천상태가 아닌 게시물을 취소하려는 경우
     **/
    void cancel(Long userId, Long postId);
}
