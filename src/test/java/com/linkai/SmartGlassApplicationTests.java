package com.linkai;

import com.google.gson.Gson;
import com.linkai.handlers.MyWebSocketHandler;
import com.linkai.model.AppResult;
import com.linkai.model.GPRS;
import com.linkai.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartGlassApplicationTests {
//
//	@Autowired
//	private static Gson gson;
//	@Autowired
//	private static MyWebSocketHandler myWebSocketHandler;
//	@Autowired
//	public SmartGlassApplicationTests(MyWebSocketHandler myWebSocketHandler,Gson gson) {
//		this.myWebSocketHandler = myWebSocketHandler;
//		this.gson = gson;
//	}

	@Autowired
	private NumberUtil numberUtil;

	@Test
	public void contextLoads() throws Exception{
//		myWebSocketHandler.sendMessageToUser("gid",new TextMessage(gson.toJson(new AppResult<>(new GPRS(1.2,2.1)))));
		String first[] = "lo:0.000000".split(":");
		String second[] = "la:0.000000".split(":");
		System.out.println(numberUtil.isDouble(first[1])&&numberUtil.isDouble(second[1]));
	}

}
