package com.dev.kioki.domain.inquire.repository;

import com.dev.kioki.domain.inquire.entity.InquireCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquireCategoryRepository extends JpaRepository<InquireCategory, Long> {
}