package com.zz.psmback.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(200,"请求成功!"),
    ERROR(500,"服务器响应错误！"),
    USER_REGISTER_FAILED(1001, "注册失败"),
    USER_ACCOUNT_EXISTED(1002,"用户名已存在"),
    USER_ACCOUNT_NOT_EXIST(1003,"用户名不存在"),
    USERNAME_PASSWORD_ERROR(1004,"用户名或密码错误"),
    PASSWORD_ERROR(1005,"密码错误"),
    SELECT_ERROR(1006,"查询出错"),
    SELECT_SUCCESS(1007,"查询成功"),
    UPDATE_SUCCESS(1008,"更新成功"),
    UPDATE_ERROR(1009,"更新出错"),
    INSERT_SUCCESS(1010,"添加成功"),
    INSERT_ERROR(1011,"添加出错"),
    DELETE_SUCCESS(1012,"删除成功"),
    DELETE_ERROR(1013,"删除出错"),
//    USER_ACCOUNT_EXPIRED(1006,"账号过期")
//    USER_ACCOUNT_EXPIRED(1006,"账号过期"),
//    USER_PASSWORD_EXPIRED(1007,"密码过期"),
    USER_ACCOUNT_DISABLE(1008,"账号不可用"),
    USER_ACCOUNT_LOCKED(1009,"账号锁定"),
    USER_NOT_LOGIN(1010,"用户未登陆"),
    USER_NO_PERMISSIONS(1011,"用户权限不足"),
//    USER_SESSION_INVALID(1012,"会话已超时"),
//    USER_ACCOUNT_LOGIN_IN_OTHER_PLACE(1013,"账号超时或账号在另一个地方登陆"),
    TOKEN_VALIDATE_FAILED(1014,"Token令牌验证失败"),
//    LIKE_ALREADY_GICED(1015,"请勿重复点赞"),
    /** 20XX 表示服务器错误 */
    PICTURE_UPLOAD_FAILED(2001,"上传图片失败"),
    GIVE_LIKE_FAILED(2002,"点赞失败"),
    PICTURE_LOAD_FAILED(2003,"图片加载失败"),
    UPDATE_USER_INFO_FAILED(2004,"修改用户信息失败"),
    UPDATE_USER_PASSWORD_FAILED(2005,"修改密码失败"),
    UNAUTHORIZED(401,"未认证");
    private final Integer code;
    private final String message;

}
