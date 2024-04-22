package com.zz.psmback.common.result;

import javafx.scene.input.KeyCodeCombination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(200,"请求成功!"),
    LOGIN_SUCCESS(999,"登录成功"),
    LOGIN_FAILED(1000,"登录失败"),
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
    DOWNLOAD_ERROR(1014,"下载错误"),
    DOWNLOAD_SUCCESS(1015,"下载成功"),
    FILE_NOT_FOUND(1016,"未找到该文件"),
    FILE_EXISTED(1017,"该文件已存在,请修改文件名"),
    UPLOAD_FAILED(1018,"上传失败"),
    UPLOAD_SUCCESS(1019,"上传成功"),
    USER_NO_PERMISSIONS(1020,"用户权限不足"),
    TOKEN_VALIDATE_FAILED(1021,"Token令牌验证失败"),
    LOGIN_OUTDATED(1022,"登录状态过期，请重新登录"),
    TASK_ASSIGNED(1023,"任务已被分配"),
    TASK_ASSIGN_SUCCESS(1024,"任务分配成功"),
    TASK_ASSIGN_FAILED(1025,"任务分配失败"),
    ACTIVE(1026,"活跃"),
    PICTURE_UPLOAD_FAILED(2001,"上传图片失败"),
    GIVE_LIKE_FAILED(2002,"点赞失败"),
    PICTURE_LOAD_FAILED(2003,"图片加载失败"),
    UPDATE_USER_INFO_FAILED(2004,"修改用户信息失败"),
    UPDATE_USER_PASSWORD_FAILED(2005,"修改密码失败"),
    UNAUTHORIZED(401,"未认证");

    private final Integer code;
    private final String message;

}
