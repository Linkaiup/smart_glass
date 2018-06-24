package com.linkai.service.impl;

import com.linkai.dao.UserRepository;
import com.linkai.dto.RequestResult;
import com.linkai.enums.StateEnum;
import com.linkai.model.User;
import com.linkai.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 13:40
 * Remember to sow in the spring.
 * Description :
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public RequestResult login(Map<String,String> map){
        String account = map.get("account");
        String password = map.get("password");
        User user = userRepository.findByAccount(account);
        if (user != null){
            if (password.equals(user.getPassword())){
                return new RequestResult(StateEnum.OK);
            }else {
                return new RequestResult(StateEnum.PASSWORD_ERROR);
            }
        }else {
            return new RequestResult(StateEnum.ACCOUNT_NOEXIST);
        }
    }
}
