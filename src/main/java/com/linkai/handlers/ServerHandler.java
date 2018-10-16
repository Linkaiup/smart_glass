package com.linkai.handlers;

import com.google.gson.Gson;
import com.linkai.model.AppResult;
import com.linkai.model.BaiduResult;
import com.linkai.model.GPRS;
import com.linkai.service.ContactService;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private ContactService contactService;

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

        String result="";
        String help = "help";
        float longitude;
        float latitude;
        long messageTime;
        Timestamp timestamp= Timestamp.valueOf(LocalDateTime.now());
        messageTime = timestamp.getTime();
        if(!(msg).contains(help)) {
            //开始切分字符串,格式：lo:23.77,la:133.22
            String[] value = msg.split(",");
            log.info("lo=" + value[0]);
            log.info("la=" + value[1]);
            String lo[] = value[0].split(":");
            String la[] = value[1].split(":");
            longitude = Float.parseFloat(lo[1]);
            latitude = Float.parseFloat(la[1]);
            GPRS gprs = new GPRS(longitude, latitude, "", messageTime);
            if (0.000000==longitude || 0.000000==latitude){
                log.info("经纬度不正常，不做任何操作");
            }else {
                result = getGprsDetailService.GetLocationString(longitude, latitude);
                //发送给移动端的 GPRS 点
                if (myWebSocketHandler.exist("gid")) {
                    log.info("开始向移动端发送信息:+{0}", new AppResult<>(gprs));
                    myWebSocketHandler.sendMessageToUser("gid", new TextMessage(gson.toJson(new AppResult<>(gprs))));
                    log.info("向移动端发送经纬度信息成功");
                }else {
                    //将途径点位置信息保存起来
                    getGprsDetailService.savePosition(longitude,latitude,messageTime);
                }
                //实时更新最后一个经纬度的信息
                getGprsDetailService.savePositionAndTime(longitude,latitude,messageTime);
            }
        }else {
            //开始切分字符串,格式：help!lo:23.77,la:133.22
            String[] value = msg.split("!");
            System.out.println("msg=" + value[1]);
            String valueTrue[] = value[1].split(",");
            String lo[] = valueTrue[0].split(":");
            String la[] = valueTrue[1].split(":");
            longitude = Float.parseFloat(lo[1]);
            latitude = Float.parseFloat(la[1]);
            result = "报警成功！";
            log.info("向移动端发送报警信息成功");
            if (myWebSocketHandler.exist("gid")){
                myWebSocketHandler.sendMessageToUser("gid", new TextMessage(gson.toJson(new AppResult<>(new GPRS(latitude, longitude,"help",messageTime)))));
            }else {
                if (contactService.saveWarning()){
                    log.info("最后一个点的报警信息保存成功");
                }
                //将所有报警点的位置信息保存起来
                getGprsDetailService.saveWarningPoint(longitude,latitude,messageTime);
            }
            if (contactService.sendWarning()){
                log.info("报警短信发送成功");
            }else {
                log.info("报警短信发送失败");
            }
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
