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

        // Assign the User to the Model
        model.setUser(user);

        // Save and return the updated Model
        modelRepository.save(model);
        return modelRepository.findById(modelId).get();
    }
}
