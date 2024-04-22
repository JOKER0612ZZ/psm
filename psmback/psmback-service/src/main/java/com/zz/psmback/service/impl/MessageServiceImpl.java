package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zz.psmback.common.entity.Message;
import com.zz.psmback.common.entity.vo.MessageView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.dao.MessageDao;
import com.zz.psmback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    @Override
    public CommonResult<?> getMessages(int receiverId) {
        try{
            List<MessageView> messages = messageDao.getMessages(receiverId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                    ResponseCode.SELECT_SUCCESS.getMessage(),messages);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }

    @Override
    public int addMessage(Message message) {
        try{
            return messageDao.insert(message);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void isRead(int messageId) {
        LambdaUpdateWrapper<Message> wrapper =new LambdaUpdateWrapper<>();
        wrapper.set(Message::getIsRead,1).eq(Message::getMessageId,messageId);
        try{
            messageDao.update(null,wrapper);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public CommonResult<?> deleteMessages(int messageId) {
        try{
            messageDao.deleteById(messageId);
            return CommonResult.success(ResponseCode.DELETE_SUCCESS.getCode(),
                    ResponseCode.DELETE_SUCCESS.getMessage(),null);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.DELETE_ERROR.getCode(),
                    ResponseCode.DELETE_ERROR.getMessage(),null);
        }
    }
}
