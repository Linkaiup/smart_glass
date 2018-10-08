package com.linkai.service;

import com.linkai.dto.RequestResult;

import java.util.Map;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 15:36
 * Remember to sow in the spring.
 * Description :
 */
public interface ContactService {

    /**
     * 添加联系人
     * @param map
     * @return RequestResult
     */
    RequestResult addPersonToContact(Map<String,String> map);

    /**
     * 删除联系人
     * @param id
     * @return
     */
    RequestResult deletePersonToContact(Integer id);

    /**
     * 展示所有联系人
     * @return
     */
    RequestResult showPersonToContact();

    /**
     * 更新联系人信息
     * @return
     */
    RequestResult updatePersonToContact(Map<String,Object> map);

    /**
     * 群发短信通知
     * @return
     */
    boolean sendWarning();

    /**
     * 保存报警信息，等待app打开时报警
     * @return boolean
     */
    boolean saveWarning();

    /**
     * 删除缓存的报警信息
     * @return boolean
     */
    boolean deleteWarning();


}
