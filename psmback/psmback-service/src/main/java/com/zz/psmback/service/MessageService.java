package com.zz.psmback.service;

import com.zz.psmback.common.entity.Message;
import com.zz.psmback.common.result.CommonResult;

import java.util.List;

public interface MessageService {
    CommonResult<?> getMessages(int receiverId);
    int addMessage(Message message);
    void isRead(int messageId);
    CommonResult<?> deleteMessages(int messageId);
}
