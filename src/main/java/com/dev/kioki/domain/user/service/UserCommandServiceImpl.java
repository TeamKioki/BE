package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final ModelRepository modelRepository;

    @Override
    public User getUser(Long user_id) {
        return userRepository.findById(user_id).get();
    }

    @Transactional
    @Override
    public Model addModelToUser(Long userId, Long modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("키오스크를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // Check if the association already exists
        if (user.getModelList().contains(model)) {
            throw new RuntimeException("저장하지 않은 키오스크입니다.");
        }

        // Add the association
        user.addModel(model);

        // Save the user (this should cascade to the join table)
        userRepository.save(user);

        return model;
    }

    @Transactional
    @Override
    public List<Model> deleteModelFromUser(Long userId, Long modelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("키오스크를 찾을 수 없습니다."));

        if (!user.getModelList().contains(model)) {
            throw new RuntimeException("저장하지 않은 키오스크입니다.");
        }

        //delete model
        user.removeModel(model);

        return user.getModelList();
    }
}
