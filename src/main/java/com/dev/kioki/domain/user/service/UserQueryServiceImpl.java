package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.repository.ReviewRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<Review> getReviewList(Long user_id, Integer page) {

        User user = userRepository.findById(user_id).get();
        Page<Review> userPage = reviewRepository.findAllByUser(user, PageRequest.of(page - 1, 10));
        return userPage;
    }


}
