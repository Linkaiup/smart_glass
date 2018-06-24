package com.linkai.exception;

import com.linkai.enums.StateEnum;
import lombok.Getter;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 15:52
 * Remember to sow in the spring.
 * Description :
 */
public class SmartGlassException extends RuntimeException{
    @Getter
    private StateEnum stateEnum;

    public SmartGlassException(StateEnum stateEnum){
        super(stateEnum.getInfo());
        this.stateEnum = stateEnum;
    }
}
