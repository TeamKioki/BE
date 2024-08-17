package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.group.Handler.GroupHandler;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.common.code.status.ErrorStatus;
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
                .orElseThrow(() -> new GroupHandler(ErrorStatus.MODEL_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));

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
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));

        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.MODEL_NOT_FOUND));

        if (!user.getModelList().contains(model)) {
            throw new GroupHandler(ErrorStatus.USER_MODEL_NOT_FOUND);
        }

        //delete model
        user.removeModel(model);

        return user.getModelList();
    }
}
