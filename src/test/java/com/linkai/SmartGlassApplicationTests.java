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

		Map<String, String> params = new HashMap<String, String>();//请求参数集合
		String apikey = "9d97e5a386f8a0d1c9ee2903599679dc";
		String text = "【QG工作室】拐杖使用者出现摔倒情况，请尽快打开 app 进行救援。";
		String mobile = "";
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		httpClientService.post("https://sms.yunpian.com/v2/sms/batch_send.json",params);
	}

}
