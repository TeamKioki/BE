package com.dev.kioki.domain.group.repository;

import com.dev.kioki.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
