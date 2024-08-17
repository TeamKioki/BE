package com.dev.kioki.domain.user.repository;

import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    boolean existsByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.phone LIKE %:query% OR u.name LIKE %:query%")
    public List<User> searchByPhoneOrName(@Param("query") String query);
}
