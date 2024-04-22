package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zz.psmback.common.entity.vo.MessageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@TableName("message")
public class Message {
    @TableId(type=IdType.AUTO)
    private int messageId;
    private String message;
    private int publisherId;
    private int receiverId;
    private String creationTime;
    private int isRead;
    private String title;
    public Message(){
    }
    public Message(MessageView messageView){
        this.messageId = messageView.getMessageId();
        this.message = messageView.getMessage();
        this.publisherId = messageView.getPublisherId();
        this.receiverId = messageView.getReceiverId();
        this.creationTime = messageView.getCreationTime();
    }
}
