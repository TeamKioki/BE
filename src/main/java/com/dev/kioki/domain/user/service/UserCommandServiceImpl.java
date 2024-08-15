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

    @Override
    public Model addModelToUser(Long user_id, Long modelId) {
        // Fetch the Model by modelId
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        // Fetch the User by userId
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //이미 모델 추가했으면
        if (model.getUser() != null && model.getUser().equals(user)) {
            throw new RuntimeException("This kiosk is already registered to the user.");
        }

        // Assign the User to the Model
        model.setUser(user);

        // Save and return the updated Model
        modelRepository.save(model);
        return modelRepository.findById(modelId).get();
    }

    @Override
    public void removeModelFromUser(Long user_id, Long modelId) {
        // Fetch the Model by modelId
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        // Check if the Model belongs to the specified User
        if (model.getUser() == null || !model.getUser().getId().equals(user_id)) {
            throw new RuntimeException("이 키오스크 모델이 목록에 없습니다.");
        }

        // Disassociate the Model from the User
        model.setUser(null);

        // Save the updated Model entity
        modelRepository.save(model);
    }
}
