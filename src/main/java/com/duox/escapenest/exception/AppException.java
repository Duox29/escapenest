package com.duox.escapenest.exception;

import com.duox.escapenest.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppException extends RuntimeException{
    ResultCode resultCode;
}
