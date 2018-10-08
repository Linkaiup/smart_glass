package com.linkai.service;

/**
 * Created by K Lin
 * on 2017/12/4.
 * at 18:26
 * description : smart_glasses
 */
public interface GetGprsDetailService {
    String GetLocationString(float longitude, float latitude) throws Exception;

    boolean InsertGPRS(int date,float longitude,float latitude)throws Exception;

    /**
     * 保存最后出现的位置信息
     */
    void savePositionAndTime(float longitude,float latitude,float time);

    /**
     * 获取最后出现的纬度
     * @return
     */
    float getLatitude();

    /**
     * 获取最后出现的经度
     * @return
     */
    float getLongitude();

    /**
     * 获取报警时间
     * @return
     */
    long getWarningTime();
}
