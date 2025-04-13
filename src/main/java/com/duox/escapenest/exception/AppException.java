package com.duox.escapenest.exception;

import com.duox.escapenest.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{
    ResultCode resultCode;
    public AppException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public AppException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
    public AppException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }
}
