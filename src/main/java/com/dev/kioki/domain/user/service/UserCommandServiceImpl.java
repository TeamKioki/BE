package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("Model not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the association already exists
        if (user.getModelList().contains(model)) {
            throw new RuntimeException("This model is already associated with the user.");
        }

        // Add the association
        user.addModel(model);

        // Save the user (this should cascade to the join table)
        userRepository.save(user);

        return model;
    }

}
