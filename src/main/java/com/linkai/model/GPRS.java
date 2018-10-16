package com.linkai.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author  K Lin
 * Date: 2018/4/16.
 * Time: 21:35
 * Remember to sow in the spring.
 * Description : smart_glass
 */
@AllArgsConstructor
@Data
public class GPRS implements Serializable{
    private static final long serialVersionUID = -3946734305303957850L;
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 经度
     */
    private double longitude;

    /**
     * 报警信息
     */
    private String info;

    /**
     * 报警时间戳
     */
    private long warningTime;
}
