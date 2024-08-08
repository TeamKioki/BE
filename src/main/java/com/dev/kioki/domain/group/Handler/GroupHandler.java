package com.dev.kioki.domain.group.Handler;

import com.dev.kioki.global.common.code.BaseErrorCode;
import com.dev.kioki.global.error.GeneralException;

public class GroupHandler extends GeneralException {

    public GroupHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}