package com.zz.psmback.WebSocket;

import com.zz.psmback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @author zouzan
 * @date 2024/04/09
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Autowired
    public void setMessageService(MessageService messageService){
        WebSocket.messageService=messageService;
    }
}
