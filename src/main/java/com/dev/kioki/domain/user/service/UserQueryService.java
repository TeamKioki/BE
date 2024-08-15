package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> findUser(Long id);

    Optional<User> getUserInfo(Long user_id);
    Page<Review> getReviewList(Long user_id, Integer page);

    Page<Inquire> getInquireList(Long user_id, Integer page);

    List<User> searchUsers(String query);

    List<Model> getModelsByUser(Long user_id);
}
