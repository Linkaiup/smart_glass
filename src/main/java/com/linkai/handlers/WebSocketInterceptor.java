package com.linkai.handlers;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import java.util.Map;

/**
 * Created by K Lin
 * Date: 2018/4/16.
 * Time: 20:02
 * Remember to sow in the spring.
 * Description : smart_glass
 */
@Service
public class WebSocketInterceptor implements HandshakeInterceptor{
    private static final String SESSION_USER = "user";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手");
        if (request instanceof ServletServerHttpRequest) {
            System.out.println("握手请求 request");
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            // 标记用户,url“?”后面直接跟要连接的glassId
            String connectURI = serverHttpRequest.getURI().toString();
            String gid = connectURI.substring(connectURI.lastIndexOf("?") + 1, connectURI.length());
            if (!"".equals(gid)) {
                attributes.put("gid", gid);
            } else {
                attributes.put("gid", "o1x0C0TjXP62Yn-mqxhVD-mOVAiY");
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
