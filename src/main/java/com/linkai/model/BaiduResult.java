package com.linkai.model;

import lombok.*;

/**
 * @author K Lin
 * on 2017/12/4.
 * at 22:52
 * description : smart_glasses
 */
@AllArgsConstructor
@Data
public class BaiduResult<T> {
    /**
     * 状态码
     */
    private int status;
    /**
     * 返回的数据
     */
    private T result;
}
