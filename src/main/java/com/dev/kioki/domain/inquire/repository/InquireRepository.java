package com.dev.kioki.domain.inquire.repository;

import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquireRepository extends JpaRepository<Inquire, Long> {

    Page<Inquire> findAllByUser(User user, PageRequest pageRequest);
}
