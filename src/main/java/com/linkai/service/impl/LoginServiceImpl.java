package com.linkai.service.impl;

import com.linkai.dao.UserRepository;
import com.linkai.dto.RequestResult;
import com.linkai.enums.StateEnum;
import com.linkai.model.User;
import com.linkai.service.ContactService;
import com.linkai.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 13:40
 * Remember to sow in the spring.
 * Description :
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private ContactService contactService;

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

    /**
     * 根据电话号码生成验证码，并存入缓存中。用于后面比对
     * @param phone
     * @return
     */
    @Override
    public RequestResult setPhoneAndAuthCode(String phone){
        //如果电话号码为空
        if (phone == null){
            return new RequestResult(StateEnum.EMPTY);
        }
        //生成1000-9999以内的随机数
        int max = 9999;
        int min = 1000;
        Random random = new Random();
        //首先生成0-9999的随机数，然后对(9999-1000+1)取模得到[0-1000]之间的随机数，然后加上min=1000，最后生成的是1000-9999的随机数
        int r = random.nextInt(max)%(max-min+1) + min;
        //将随机数转成字符串
        String authCode = String.valueOf(r);
        log.info("authCode is"+ authCode);
        log.info("phone is"+phone);
        sendAuthCode(phone,authCode);
        //初始化redis集合
        RedisSerializer redisSerializer =new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        //往redis集合中添加键值对
        vo.set(phone, authCode);
        return new RequestResult(StateEnum.OK);
    }

    @Override
    public RequestResult compareAuthByPhone(String phone,String authCode){
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        String trueCode = (String) vo.get(phone);
        if (trueCode.equals(authCode)){
            return new RequestResult(StateEnum.OK);
        }else {
            return new RequestResult(StateEnum.PASSWORD_ERROR);
        }
    }

    @Override
    public boolean sendAuthCode(String phone,String authCode) {
        //请求参数集合
        Map<String, String> params = new HashMap(3);
        String apikey = "9d97e5a386f8a0d1c9ee2903599679dc";
        String text = "【QG工作室】您的登录验证码为:" + authCode;
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", phone);
        httpClientService.post("https://sms.yunpian.com/v2/sms/batch_send.json",params);
        return true;
    }

    @Override
    public boolean showWarning(){
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        String flag = (String) vo.get("help");
        log.info("flag is:"+flag);
        if (("true").equals(flag)){
            contactService.deleteWarning();
            return true;
        }else {
            return false;
        }
    }
}
