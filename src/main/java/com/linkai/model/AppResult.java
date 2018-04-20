package com.linkai.model;

/**
 * Created by K Lin
 * Date: 2018/4/16.
 * Time: 21:23
 * Remember to sow in the spring.
 * Description : smart_glass
 */
public class AppResult<T> {
    private T result;             // 交给移动端的数据

    public T getResult() {
        return result;
    }

    public AppResult(T result) {
        this.result = result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaiduResult{" +
                ", result=" + result +
                '}';
    }
}
