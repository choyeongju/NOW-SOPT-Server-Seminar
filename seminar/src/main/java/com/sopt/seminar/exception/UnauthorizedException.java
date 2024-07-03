package com.sopt.seminar.exception;

import com.sopt.seminar.common.dto.ErrorMessage;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
