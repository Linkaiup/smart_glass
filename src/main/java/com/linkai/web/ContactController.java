package com.linkai.web;

import com.linkai.dto.RequestResult;
import com.linkai.enums.StateEnum;
import com.linkai.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 15:35
 * Remember to sow in the spring.
 * Description :
 */
@Slf4j
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping("/add")
    public RequestResult addPersonToContact(@RequestBody Map<String,String> map){
        if (!map.containsKey("name") || !map.containsKey("phone") || map.get("name") == null || map.get("phone") == null){
            return new RequestResult(StateEnum.EMPTY);
        }
        return contactService.addPersonToContact(map);
    }

    @RequestMapping("/delete")
    public RequestResult deletePersonToContact(@RequestBody Map<String,Integer> map){
        if (!map.containsKey("id") || map.get("id") == null){
            return new RequestResult(StateEnum.EMPTY);
        }
        return contactService.deletePersonToContact(map.get("id"));
    }

    @RequestMapping("/show")
    public RequestResult showPersonToContact(){
        return contactService.showPersonToContact();
    }

    @RequestMapping("/update")
    public RequestResult updatePersonToContact(@RequestBody Map<String,Object> map){
        boolean update1 = !map.containsKey("name") && !map.containsKey("phone");
        boolean update2 = map.get("name") == null && map.get("phone") == null;
        if (!map.containsKey("id") || map.get("id") == null || update1 || update2){
            return new RequestResult(StateEnum.EMPTY);
        }
        return contactService.updatePersonToContact(map);
    }
}
