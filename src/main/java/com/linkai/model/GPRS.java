package com.linkai.model;

/**
 * Created by K Lin
 * Date: 2018/4/16.
 * Time: 21:35
 * Remember to sow in the spring.
 * Description : smart_glass
 */
public class GPRS {
    private double latitude;         // 经度
    private double longitude;         // 纬度

    public GPRS(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GPRS{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
