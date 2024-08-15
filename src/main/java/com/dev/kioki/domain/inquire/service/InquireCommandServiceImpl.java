package com.dev.kioki.domain.inquire.service;

import com.dev.kioki.domain.inquire.converter.InquireConverter;
import com.dev.kioki.domain.inquire.dto.InquireRequestDTO;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.inquire.entity.InquireCategory;
import com.dev.kioki.domain.inquire.repository.InquireCategoryRepository;
import com.dev.kioki.domain.inquire.repository.InquireRepository;
import com.dev.kioki.domain.inquire.service.InquireCommandService;
import com.dev.kioki.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InquireCommandServiceImpl implements InquireCommandService {

    private final UserRepository userRepository;
    private final InquireRepository inquireRepository;
    private final InquireCategoryRepository inquireCategoryRepository;

    @Override
    public Inquire createInquire(Long userId, InquireRequestDTO.InquireDTO request) {
        Inquire inquire = InquireConverter.toInquire(request);

        inquire.setUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다.")));

        // 카테고리 설정
        InquireCategory category = inquireCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        inquire.setCategory(category);

        return inquireRepository.save(inquire);
    }
}
