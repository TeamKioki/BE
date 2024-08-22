package com.dev.kioki.domain.review.Handler;

import com.dev.kioki.global.common.code.BaseErrorCode;
import com.dev.kioki.global.error.GeneralException;

public class ReviewHandler extends GeneralException {
    public ReviewHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
