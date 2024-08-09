package com.dev.kioki.domain.inquire.service;

import com.dev.kioki.domain.inquire.dto.InquireRequestDTO;
import com.dev.kioki.domain.inquire.entity.Inquire;

public interface InquireCommandService {

    Inquire createInquire(Long user_id, InquireRequestDTO.InquireDTO request);
}
