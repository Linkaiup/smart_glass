package com.linkai.service.impl;

import com.google.gson.Gson;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpClientService {
    private final CloseableHttpClient httpClient;
    private final RequestConfig requestConfig;

    @Autowired(required = false)
    public HttpClientService(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    /**
     * 提交和接收json数据，接收后返回的是一个Map的对象
     *
     * @param url 请求url
     * @return Map<String, Object>
     * @throws IOException IOException
     */
    public String doGetJsonMap(String url) throws IOException {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);
        // 装载配置信息
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;

        try {
            // 执行请求
            response = this.httpClient.execute(httpGet);
            String middleResult = EntityUtils.toString(response.getEntity(), "UTF-8");
            middleResult = middleResult.substring(29, middleResult.length() - 1);
            System.out.println("this is ->" + middleResult + "and end");
            return middleResult;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

}
