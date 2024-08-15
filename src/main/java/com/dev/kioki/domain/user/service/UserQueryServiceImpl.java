package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.group.repository.GroupMemberRepository;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.inquire.repository.InquireRepository;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.repository.ReviewRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final InquireRepository inquireRepository;
    private final ModelRepository modelRepository;

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserInfo(Long user_id) { return findUser(user_id); }

    @Override
    public Page<Review> getReviewList(Long user_id, Integer page) {

        User user = userRepository.findById(user_id).get();
        Page<Review> userPage = reviewRepository.findAllByUser(user, PageRequest.of(page - 1, 10));
        return userPage;
    }

    @Override
    public Page<Inquire> getInquireList(Long user_id, Integer page) {

        User user = userRepository.findById(user_id).get();
        Page<Inquire> userPage = inquireRepository.findAllByUser(user, PageRequest.of(page-1, 10));
        return userPage;
    }

    public List<User> searchUsers(String query) {
        List<User> users;

        if (query != null && !query.isEmpty()) {
            users = userRepository.searchByPhoneOrName(query);
        } else {
            users = userRepository.findAll();
        }
        return users;
    }

    @Override
    public List<Model> getModelsByUser(Long user_id) {
       return modelRepository.findByUserId(user_id);
    }
}
