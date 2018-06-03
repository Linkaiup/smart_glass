package com.linkai.model;

import lombok.*;

/**
 * Created by K Lin
 * Date: 2018/4/16.
 * Time: 21:23
 * Remember to sow in the spring.
 * Description : smart_glass
 */
@AllArgsConstructor
@Data
public class AppResult<T> {
    /**
     * 交给移动端的数据
     */
    private T result;
}
