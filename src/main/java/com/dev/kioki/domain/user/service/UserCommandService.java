package com.dev.kioki.domain.user.service;

import com.dev.kioki.domain.user.entity.User;

public interface UserCommandService {
    User getUser(Long user_id);
}
