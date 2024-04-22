package com.zz.psmback.WebSocket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.zz.psmback.common.entity.Message;
import com.zz.psmback.common.entity.vo.MessageView;
import com.zz.psmback.dao.MessageDao;
import com.zz.psmback.service.MessageService;
import com.zz.psmback.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据**/
    private Session session;
    /**
     * 用户ID
     **/
    private int userId;

    protected static MessageService messageService;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
        虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
        注：底下WebSocket是当前类名**/

    private static final CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    /**用来存在线连接用户信息**/
    private static final ConcurrentHashMap<Integer,Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     **/
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")int userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:"+message);
        List<MessageView> parseMessage= JSON.parseArray(message, MessageView.class);
        sendMoreMessage(parseMessage);
        Date date= new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Message m : parseMessage){
            String dateString = dateFormat.format(date);
            m.setCreationTime(dateString);
            int result= messageService.addMessage(m);
        }
        log.info("【websocket消息】解析后的消息:"+parseMessage);
    }
    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }


    /** 此为单点消息(多人)**/
    public void sendMoreMessage(List<MessageView> messages) {
        for(MessageView message:messages) {
            int receiverId = message.getReceiverId();
            Session session = sessionPool.get(receiverId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(JSON.toJSONString(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
