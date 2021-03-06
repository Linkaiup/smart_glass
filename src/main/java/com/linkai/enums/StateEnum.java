package com.linkai.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 14:04
 * Remember to sow in the spring.
 * Description :
 */
public enum StateEnum {
    /**
     * 状态码
     */
    OK(200,"操作正常"),
    EMPTY(501,"参数为空"),
    ACCOUNT_NOEXIST(51,"电话不存在"),
    PASSWORD_ERROR(52,"验证码错误"),
    NAME_TOLONG(53,"联系人名称太长"),
    PHONE_TOLONG(54,"电话太长"),
    CONTACT_NOEXIST(55,"联系人不存在"),
    WARNING(56,"需要报警"),
    NO_WARNING(57,"不需要报警"),
    NO_DATA(58,"没有途经路线数据");

    /**
     * 状态码
     */
    @Getter
    @Setter
    private int state;

    /**
     * 状态信息
     */
    @Getter
    @Setter
    private String info;

    StateEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }
}
