package com.linkai.model;

/**
 * Created by K Lin
 * on 2017/12/4.
 * at 22:52
 * description : smart_glasses
 */
public class BaiduResult<T> {
    private int status;          // 状态码
    private T result;             // 返回的数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaiduResult{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
