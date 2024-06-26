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
//    public static <T> CommonResult<T> error(boolean isSuccess,int code,String message,T data){
//        return new CommonResult<T>(isSuccess,code, message,data);
//    }
//    public static <T> CommonResult<T> success(boolean isSuccess,int code,String message,T data){
//        return new CommonResult<T>(isSuccess,code, message,data);
//    }
    public static <T> CommonResult<T> message(boolean isSuccess,int code,String message,T data){
        return new CommonResult<T>(isSuccess,code, message,data);
    }
    public static <T> CommonResult<T> error(int code,String message,T data){
        return new CommonResult<T>(false,code, message,data);
    }
    public static <T> CommonResult<T>success(int code,String message,T data){
        return new CommonResult<T>(true,code, message,data);
    }
}
