package com.linkai.model;

import lombok.*;

/**
 * @author  K Lin
 * Date: 2018/4/16.
 * Time: 21:35
 * Remember to sow in the spring.
 * Description : smart_glass
 */
@AllArgsConstructor
@Data
public class GPRS {
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
}
