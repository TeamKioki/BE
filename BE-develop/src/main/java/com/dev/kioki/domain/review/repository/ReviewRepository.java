package com.dev.kioki.domain.review.repository;

import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByUser(User user, PageRequest pageRequest);
}
