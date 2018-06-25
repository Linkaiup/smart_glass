package com.linkai.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientService {
    private final CloseableHttpClient httpClient;
    private final RequestConfig requestConfig;
    private Gson gson = new Gson();

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

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public String post(String url, Map < String, String > paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List< NameValuePair > paramList = new ArrayList<
                                        NameValuePair >();
                for (Map.Entry < String, String > param: paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(),
                            param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList,
                        "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
