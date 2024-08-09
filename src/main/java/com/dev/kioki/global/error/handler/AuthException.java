package com.dev.kioki.global.error.handler;

import com.dev.kioki.global.common.code.status.ErrorStatus;
import com.dev.kioki.global.error.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(ErrorStatus errorStatus) {super(errorStatus);}
}
