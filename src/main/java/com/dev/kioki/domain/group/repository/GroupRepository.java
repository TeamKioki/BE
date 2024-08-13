package com.dev.kioki.domain.group.repository;

import com.dev.kioki.domain.group.entity.Group;
import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
//    @Query("SELECT g.groupId FROM Group g WHERE g.owner.id = :userId")
//    Optional<Long> findGroupIdByOwnerId(@Param("userId") Long userId);

    @Query("SELECT g.groupId FROM Group g WHERE g.owner = :owner")
    Optional<Long> findGroupIdByOwner(@Param("owner") User owner);
}
