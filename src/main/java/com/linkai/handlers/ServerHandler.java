package com.linkai.handlers;

import com.linkai.service.impl.GetGprsDetailServiceImpl;
import com.linkai.util.NumberUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Autowired
    private GetGprsDetailServiceImpl getGprsDetailService;
    @Autowired
    private NumberUtil numberUtil;

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

        String value[] =msg.split(",");//开始切分字符串
        System.out.println("lo="+value[0]);
        System.out.println("la="+value[1]);
        String lo[] = value[0].split(":");
        String la[] = value[1].split(":");
        float longitude;
        float latitude;
        String result;
        if(numberUtil.isDouble(lo[1])&&numberUtil.isDouble(la[1])){
            longitude = Float.parseFloat(lo[1]);
            latitude = Float.parseFloat(la[1]);
            result = getGprsDetailService.GetLocationString(longitude,latitude);
        }else{
            result = "请求经纬度参数有误！请重新请求！";
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
