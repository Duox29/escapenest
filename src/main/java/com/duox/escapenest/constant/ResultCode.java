package com.duox.escapenest.constant;
import org.springframework.http.HttpStatus;
public enum ResultCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa xác định.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(1001,"Invalid token", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(1002,"Token expired",HttpStatus.BAD_REQUEST),
    CANNOT_REFRESH_TOKEN(1003,"Can not refresh token", HttpStatus.BAD_REQUEST),


    ACCOUNT_NOT_EXISTED(2001,"Tài khoản không tồn tại",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(2002,"Không được xác thực",HttpStatus.UNAUTHORIZED),
    EMAIL_EXISTED(2003,"Email đã tồn tại",HttpStatus.BAD_REQUEST),
    ACCOUNT_PASSWORD_ERROR(2004,"Mật khẩu hoặc tài khoản không chính xác",HttpStatus.BAD_REQUEST),
    ACCOUNT_SESSION_EXPIRED(2005, "Phiên đăng nhập của tài khoản đã hết hạn, vui lòng đăng nhập lại.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_ACTIVATED(2006, "Tài khoản chưa được kích hoạt.", HttpStatus.FORBIDDEN),
    INVALID_OTP(2007,"OTP không hợp lệ", HttpStatus.BAD_REQUEST);


    private final Integer code;
    private final String message;
    private  final HttpStatus httpStatus;
    ResultCode(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
