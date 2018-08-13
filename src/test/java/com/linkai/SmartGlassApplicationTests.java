package com.linkai;

import com.linkai.service.impl.HttpClientService;
import com.linkai.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SmartGlassApplicationTests {

	@Autowired
	private HttpClientService httpClientService;
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
