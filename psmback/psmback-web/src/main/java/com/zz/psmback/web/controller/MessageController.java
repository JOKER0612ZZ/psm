package com.zz.psmback.web.controller;

import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @RequestMapping(value = "/getMessages/{userId}", method = RequestMethod.GET)
    public CommonResult<?> getMessages(@PathVariable("userId") Integer userId) {
        return messageService.getMessages(userId);
    }

    @RequestMapping(value="/deleteMessage/{messageId}",method = RequestMethod.DELETE)
    public CommonResult<?> deleteMessage(@PathVariable("messageId") Integer messageId){
        return messageService.deleteMessages(messageId);
    }

    @RequestMapping(value="/isRead/{messageId}",method = RequestMethod.PUT)
    public void isRead(@PathVariable("messageId") Integer messageId){
        messageService.isRead(messageId);
    }
}
