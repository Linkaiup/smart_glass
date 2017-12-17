package com.linkai.service.impl;

import com.google.gson.Gson;
import com.linkai.model.BaiduResult;
import com.linkai.service.GetGprsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by K Lin
 * on 2017/12/4.
 * at 18:41
 * description : smart_glasses
 */
@Service
public class GetGprsDetailServiceImpl implements GetGprsDetailService {
    private final HttpClientService httpClientService;
    private Gson gson = new Gson();

    @Autowired(required = false)
    public GetGprsDetailServiceImpl(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    @Override
    public String GetLocationString(float longitude,float latitude) throws Exception{
        String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + latitude + "," + longitude + "&output=json&pois=1&ak=U6eeOKvjHpNRQG623vWRnamxfRjQsPuA";
        BaiduResult baiduResult ;
        String stringFromHttp= httpClientService.doGetJsonMap(url);
        baiduResult = gson.fromJson(stringFromHttp, BaiduResult.class);
        if(baiduResult.getStatus()==500){
            return "所提交经纬度非法！";
        }
        System.out.println("baiduResult is ->"+baiduResult);
        Map<String ,Object> dataResult;
        dataResult = (Map<String, Object>) baiduResult.getResult();
        System.out.println("this is ->"+dataResult);
        String result = dataResult.get("formatted_address").toString();
        if(result.equals("")||result==null)
        {
            return "请求位置超出范围，无数据！";
        }
        System.out.println(result);
        return result;
    }
}
