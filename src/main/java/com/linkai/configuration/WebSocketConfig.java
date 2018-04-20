package com.linkai.configuration;

import com.linkai.handlers.MyWebSocketHandler;
import com.linkai.handlers.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

/**
 * Created by K Lin
 * Date: 2018/4/16.
 * Time: 19:46
 * Remember to sow in the spring.
 * Description : smart_glass
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {
    @Autowired
    private final MyWebSocketHandler handler;

    @Autowired
    public WebSocketConfig(MyWebSocketHandler handler) {
        this.handler = handler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(handler, "/ws").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
        registry.addHandler(handler, "/sockjs/message").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
    }
}
