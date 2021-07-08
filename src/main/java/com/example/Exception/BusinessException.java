package com.example.Exception;

import com.example.result.ResultCode;
import lombok.Getter;

/**
 * 业务自定义异常类
 */
@Getter
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;
    private ResultCode resultCode;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.tips();
    }

}