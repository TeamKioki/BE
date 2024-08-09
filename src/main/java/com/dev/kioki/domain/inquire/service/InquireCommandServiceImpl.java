package com.dev.kioki.domain.inquire.service;

import com.dev.kioki.domain.inquire.dto.InquireRequestDTO;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.inquire.repository.InquireRepository;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.domain.inquire.converter.InquireConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InquireCommandServiceImpl implements InquireCommandService {

    private final UserRepository userRepository;
    private final InquireRepository inquireRepository;

    @Override
    public Inquire createInquire(Long user_id, InquireRequestDTO.InquireDTO request) {

        Inquire inquire = InquireConverter.toInquire(request);

        inquire.setUser(userRepository.findById(user_id).get());
        return inquireRepository.save(inquire);
    }
}
