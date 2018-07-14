package com.linkai.dto;

import com.linkai.enums.StateEnum;
import lombok.Data;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 12:00
 * Remember to sow in the spring.
 * Description :
 */
@Data
public class RequestResult<T> {
    /**
     * 状态码
     */
    private int state;

    /**
     * 状态信息
     */
    private String info;

    /**
     * 数据
     */
    private T data;


    public RequestResult(StateEnum stateEnum, T data) {
        this.state = stateEnum.getState();
        this.info = stateEnum.getInfo();
        this.data = data;
    }

    public RequestResult(StateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.info = stateEnum.getInfo();
    }
}
