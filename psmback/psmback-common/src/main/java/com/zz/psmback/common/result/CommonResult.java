package com.zz.psmback.common.result;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zouzan
 * @date 2024/04/04
 */
@Data
public class CommonResult<T> implements Serializable {
    private boolean success;
    private Integer code;
    private String message;
    private T data;

    /**
     * 功能描述：
     *
     * @param success
     * @param code
     * @param message
     * @param data
     * @author zouzan
     * @date 2024/04/04
     */

    public CommonResult(Boolean success,int code,String message,T data){
        this.success=success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     *
     * @param isSuccess 是否成功
     * @param code 状态代码
     * @param message 消息
     * @param data 数据
     * 公共返回
     */
    public static <T> CommonResult<T> error(boolean isSuccess,int code,String message,T data){
        return new CommonResult<T>(isSuccess,code, message,data);
    }
    public static <T> CommonResult<T> success(boolean isSuccess,int code,String message,T data){
        return new CommonResult<T>(isSuccess,code, message,data);
    }
//    public static <T> CommonResult<T> success(T data){
//        return new CommonResult<T>(true,ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
//    }
//    public static <T> CommonResult<T> error(T data){
//        return new CommonResult<T>(false,ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(),data);
//    }
//    public static <T> CommonResult<T> user_account_not_exist(T data){
//        return new CommonResult<T>(false,ResponseCode.USER_ACCOUNT_NOT_EXIST.getCode(),ResponseCode.USER_ACCOUNT_NOT_EXIST.getMessage(),data);
//    }
//    public static <T> CommonResult<T> user_account_existed(T data){
//        return new CommonResult<T>(false,ResponseCode.USER_ACCOUNT_EXISTED.getCode(),ResponseCode.USER_ACCOUNT_EXISTED.getMessage(),data);
//    }
//    public static <T> CommonResult<T> username_password_error(T data){
//        return new CommonResult<T>(false,ResponseCode.USERNAME_PASSWORD_ERROR.getCode(), ResponseCode.USERNAME_PASSWORD_ERROR.getMessage(), data);
//    }
    public static <T> CommonResult<T> error(int code,String message,T data){
        return new CommonResult<T>(false,code, message,data);
    }
    public static <T> CommonResult<T>success(int code,String message,T data){
        return new CommonResult<T>(true,code, message,data);
    }
}
