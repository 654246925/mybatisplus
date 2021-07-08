package com.example.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */
@Data
@ApiModel(value= "Result", description="统一API响应结果封装")
public class Result<T> implements Serializable {
    @ApiModelProperty(value = "请求状态码")
    private int code;
    @ApiModelProperty(value = "状态码对应信息")
    private String message;
    @ApiModelProperty(value = "数据")
    private T data;

    public Result(){

    }

    public Result(String message){
        this.message = message;
    }

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(ResultCode resultCode, T data){
        this.code = resultCode.code();
        this.message = resultCode.tips();
        this.data = data;
    }

    public Result(int code, T data){
        this.code = code;
        this.data = data;
    }

    public Result(ResultCode resultCode){
        this.code = resultCode.code();
        this.message = resultCode.tips();
    }

    public static Result genSuccessResult() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result genSuccessResult(String message) {
        return new Result(ResultCode.SUCCESS, message);
    }

    public static <T> Result genSuccessResult(T data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result genErrorResult(String message) {
        return new Result(ResultCode.ERROR.code(), message);
    }

    public static Result genErrorResult(int code, String message) {
        return new Result(code, message);
    }

    public static Result genErrorResult(ResultCode resultCode) {
        return new Result(resultCode);
    }

}
