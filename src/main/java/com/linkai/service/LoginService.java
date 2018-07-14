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
}
