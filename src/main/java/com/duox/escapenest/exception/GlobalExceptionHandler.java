package com.duox.escapenest.exception;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.util.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResultMessage> handlingAppException(AppException e){
        ResultCode resultCode = e.getResultCode();
        return  ResponseEntity.status(resultCode.getHttpStatus())
                .body(ResultUtil.error(resultCode));
    }
}
