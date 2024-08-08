package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserQueryService {
    Optional<User> findUser(Long id);
    Page<Review> getReviewList(Long user_id, Integer page);
}
