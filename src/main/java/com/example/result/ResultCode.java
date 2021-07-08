package com.example.result;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200,"true"),//成功
    FAIL(400,"false"),//失败
    UNAUTHORIZED(401,"未认证（签名错误）"),//未认证（签名错误）
    NOT_FOUND(404,"Not Found"),
    ERROR_TOKEN(500001, "登录超时"),
    ERROR(500002, "服务器内部异常"),
    ;//接口不存在
    private final int code;
    private final String tips;

    ResultCode(int code,String tips) {
        this.code = code;
        this.tips = tips;
    }

    public int code() {
        return code;
    }
    public String tips() {
        return tips;
    }
}