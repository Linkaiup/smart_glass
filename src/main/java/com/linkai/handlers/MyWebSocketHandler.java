package com.linkai.handlers;

import com.google.gson.Gson;
import com.linkai.model.AppResult;
import com.linkai.model.GPRS;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  K Lin
 * Date: 2018/4/16.
 * Time: 19:49
 * Remember to sow in the spring.
 * Description : smart_glass
 */
@Service
@Slf4j
public class MyWebSocketHandler implements WebSocketHandler {
    @Autowired
    private final Gson gson;

    @Autowired
    public MyWebSocketHandler(Gson gson) {
        this.gson = gson;
    }

    //在线用户列表
    private static final Map<String, WebSocketSession> users = new HashMap<>();


    //用户标识
    private static final String SESSION_USER = "gid";



    /**
     * 连接建立成功之后，记录用户的连接标识，便于后面发信息
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        String gid = (String) session.getAttributes().get(SESSION_USER);
        System.out.println(gid);
        if (gid != null) {
            users.put(SESSION_USER, session);
            session.sendMessage(new TextMessage("成功建立socket连接"));
        }
        log.info("连接Socket通道数 >> : {}", users.size());
    }

    /**
     * 处理收到的websocket信息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("接受信息 >> {}", message.getPayload());
        if (message.getPayloadLength() == 0) {
            return;
        }
        message.getPayload();
        if (message.getPayload().toString().startsWith("@")) {
            return;
        }
        Map request;
        try {
            request = gson.fromJson(message.getPayload().toString(), HashMap.class);
        } catch (Exception e) {
            log.error("json转换异常 >> : {}", session.getId());
            return;
        }
        String get = (String) request.get("get");
        if (get == null) {
            log.error("数据为空异常 >> : {}", session.getId());
            return;
        }
    }

    /**
     * 向channel转发消息
     *
     * @param carId
     * @param content
     */
    private void deliverCommand(String carId, String content) {

    }


    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        if (session.isOpen()) {
            try {
                session.close();
            } catch (Exception e) {
                log.error("连接关闭 >> : {}", session.getId());
            }
        }
        // 移除Socket会话
        for (Map.Entry<String, WebSocketSession> entry : users.entrySet()) {
            if (entry.getValue().getId().equals(session.getId())) {
                users.remove(entry.getKey());
                log.info("Socket会话异常移除:用户ID : {}", entry.getKey());
                break;
            }
        }
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        String uid = (String) session.getAttributes().get("uid");

        // 移除Socket会话
        for (Map.Entry<String, WebSocketSession> entry : users.entrySet()) {
            if (entry.getValue().getId().equals(session.getId())) {
                users.remove(entry.getKey());
                log.info("Socket会话已经移除:用户ID : {}", entry.getKey());
                break;
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给某个用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(String gid, TextMessage message) throws IOException {
        WebSocketSession session = users.get(gid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }

    /**
     * 判断名称为 gid 的客户是否连接
     * @param gid
     * @return
     */
    public boolean exist(String gid){
        WebSocketSession session = users.get(gid);
        if (session != null && session.isOpen()){
            return true;
        }else {
            return false;
        }
    }
}
