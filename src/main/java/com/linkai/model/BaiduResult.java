package com.linkai.model;

import lombok.*;

/**
 * Created by K Lin
 * on 2017/12/4.
 * at 22:52
 * description : smart_glasses
 */
@AllArgsConstructor
@Data
public class BaiduResult<T> {
    private int status;          // 状态码
    private T result;             // 返回的数据
}
