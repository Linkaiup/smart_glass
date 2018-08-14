package com.linkai.web;

import com.linkai.dto.RequestResult;
import com.linkai.enums.StateEnum;
import com.linkai.service.LoginService;
import com.linkai.service.impl.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 12:12
 * Remember to sow in the spring.
 * Description :
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/request")
    public RequestResult requestAuthCode(@RequestBody  Map<String,String> map){
        if (!map.containsKey("phone") || map.get("phone") == null){
            return new RequestResult(StateEnum.EMPTY);
        }
        return loginService.setPhoneAndAuthCode(map.get("phone"));
    }

    @RequestMapping("/check")
    public RequestResult login(@RequestBody  Map<String,String> map){
        if (!map.containsKey("phone") || !map.containsKey("authCode") || map.get("phone") == null || map.get("authCode") == null){
            return new RequestResult(StateEnum.EMPTY);
        }
        return loginService.compareAuthByPhone(map.get("phone"),map.get("authCode"));
    }

    @RequestMapping("/getwarning")
    public RequestResult showWarning(){
        if (loginService.showWarning()){
            return new RequestResult(StateEnum.WARNING);
        }else {
            return new RequestResult(StateEnum.NO_WARNING);
        }
    }
}
