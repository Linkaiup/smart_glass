package com.linkai.service;

import com.linkai.dto.RequestResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 13:29
 * Remember to sow in the spring.
 * Description :
 */
public interface LoginService {
    /**
     * 登录服务
     * @param map
     * @return RequestResult
     */
    RequestResult login(Map<String,String> map);

    /**
     * 根据电话生成验证码，并添加入缓存中，为后续匹配服务
     * @param phone
     * @return
     */
    RequestResult setPhoneAndAuthCode(String phone);

    /**
     * 通过电话和验证码进行匹配
     * @param phone
     * @param authCode
     * @return
     */
    RequestResult compareAuthByPhone(String phone,String authCode);

    /**
     * 短信告知验证码
     * @return
     */
    boolean sendAuthCode(String phone,String authCode);
}
