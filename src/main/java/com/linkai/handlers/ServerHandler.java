package com.linkai.handlers;

import com.google.gson.Gson;
import com.linkai.model.AppResult;
import com.linkai.model.BaiduResult;
import com.linkai.model.GPRS;
import com.linkai.service.impl.GetGprsDetailServiceImpl;
import com.linkai.service.impl.HttpClientService;
import com.linkai.util.NumberUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.net.InetAddress;

/**
 * Created by K Lin
 * on 2017/12/7.
 * at 21:46
 * description : 处理器逻辑
 */
@Component
@Qualifier("serverHandler")
@Sharable
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {


    private final Gson gson;
    private final MyWebSocketHandler myWebSocketHandler;
    @Autowired
    private GetGprsDetailServiceImpl getGprsDetailService;
    @Autowired
    private NumberUtil numberUtil;

    @Autowired(required = false)
    public ServerHandler(Gson gson, MyWebSocketHandler myWebSocketHandler) {
        this.gson = gson;
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        log.info("client msg:"+msg);
        String clientIdToLong= ctx.channel().id().asLongText();
        log.info("client long id:"+clientIdToLong);
        String clientIdToShort= ctx.channel().id().asShortText();
        log.info("client short id:"+clientIdToShort);
            //send to client
        System.out.println("服务端收到客户端发送过来的消息为： " + msg);

        String result;
        String help = "help";
        if(!(help).equals(msg)) {
            //开始切分字符串
            String value[] = msg.split(",");
            System.out.println("lo=" + value[0]);
            System.out.println("la=" + value[1]);
            String lo[] = value[0].split(":");
            String la[] = value[1].split(":");
            float longitude;
            float latitude;
            if (numberUtil.isDouble(lo[1]) && numberUtil.isDouble(la[1])) {
                longitude = Float.parseFloat(lo[1]);
                latitude = Float.parseFloat(la[1]);
                result = getGprsDetailService.GetLocationString(longitude, latitude);
                //发送给移动端的 GPRS 点
                log.info("开始向移动端发送信息:+{0}", new AppResult<>(new GPRS(longitude, latitude,null)));
                myWebSocketHandler.sendMessageToUser("gid", new TextMessage(gson.toJson(new AppResult<>(new GPRS(longitude, latitude," ")))));
                log.info("向移动端发送经纬度信息成功");
            } else {
                result = "请求经纬度参数有误！请重新请求！";
            }
        }else {
            result = "报警成功！";
            myWebSocketHandler.sendMessageToUser("gid", new TextMessage(gson.toJson(new AppResult<>(new AppResult<>(new GPRS(0.0, 0.0,"help"))))));
            log.info("向移动端发送报警信息成功");
        }
        /**
         * 给客户端返回数据
         */

        ctx.channel().writeAndFlush(result);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        log.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        ctx.channel().writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("\nChannel is disconnected");
        super.channelInactive(ctx);
    }
}
