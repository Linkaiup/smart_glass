package com.linkai.service.impl;

import com.linkai.dao.PersonToContactRepository;
import com.linkai.dto.RequestResult;
import com.linkai.enums.StateEnum;
import com.linkai.exception.SmartGlassException;
import com.linkai.model.PersonToContact;
import com.linkai.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 15:40
 * Remember to sow in the spring.
 * Description :
 */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private PersonToContactRepository personToContactRepository;
    @Autowired
    private HttpClientService httpClientService;

    @Override
    public RequestResult addPersonToContact(Map<String, String> map) {
        //监护人名称最小长度
        int minimumName = 2;
        //监护人名称最大长度
        int maximumName = 20;

        String name = map.get("name");
        String phone = map.get("phone");
        if (name.length() < minimumName || name.length() > maximumName) {
            throw new SmartGlassException(StateEnum.NAME_TOLONG);
        } else if (phone.length() < minimumName || phone.length() > maximumName) {
            throw new SmartGlassException(StateEnum.PHONE_TOLONG);
        } else {
            PersonToContact personToContact = new PersonToContact();
            personToContact.setName(name);
            personToContact.setPhone(phone);
            personToContactRepository.save(personToContact);
            return new RequestResult<>(StateEnum.OK);
        }
    }

    @Override
    public RequestResult deletePersonToContact(Integer id) {
        if (personToContactRepository.exists(id)) {
            personToContactRepository.delete(id);
            return new RequestResult<>(StateEnum.OK);
        } else {
            return new RequestResult<>(StateEnum.CONTACT_NOEXIST);
        }
    }

    @Override
    public RequestResult showPersonToContact() {
        List<PersonToContact> personToContacts = personToContactRepository.findAll();
        //用户列表
        Map<String, List<Map<String, Object>>> personList = new HashMap<>(1);
        //用户数组
        List<Map<String, Object>> personLists = new ArrayList<>();
        for (PersonToContact personToContact : personToContacts) {
            Map<String, Object> personData = new HashMap<>(3);
            personData.put("id", personToContact.getId());
            personData.put("name", personToContact.getName());
            personData.put("phone", personToContact.getPhone());
            personLists.add(personData);
        }
        personList.put("personLists", personLists);
        log.info("展示所有联系人");
        return new RequestResult<>(StateEnum.OK, personList);
    }

    @Override
    public RequestResult updatePersonToContact(Map<String, Object> map) {
        int id = (Integer) map.get("id");
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        PersonToContact personToContact = new PersonToContact();
        personToContact.setId(id);
        personToContact.setName(name);
        personToContact.setPhone(phone);
        if (personToContactRepository.exists(id)) {
            personToContactRepository.save(personToContact);
            return new RequestResult<>(StateEnum.OK);
        } else {
            return new RequestResult<>(StateEnum.CONTACT_NOEXIST);
        }
    }

    @Override
    public boolean sendWarning() {
        //请求参数集合
        Map<String, String> params = new HashMap(3);
        String apikey = "9d97e5a386f8a0d1c9ee2903599679dc";
        String text = "【QG工作室】拐杖使用者出现摔倒情况，请尽快打开 app 进行救援。";
        String mobile = "";
        List<PersonToContact> personToContacts = personToContactRepository.findAll();
        for (PersonToContact personToContact : personToContacts) {
            mobile += personToContact.getPhone();
            mobile += ",";
        }
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        httpClientService.post("https://sms.yunpian.com/v2/sms/batch_send.json",params);
        return true;
    }
}
