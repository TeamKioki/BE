package com.dev.kioki.domain.user.repository;

import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
