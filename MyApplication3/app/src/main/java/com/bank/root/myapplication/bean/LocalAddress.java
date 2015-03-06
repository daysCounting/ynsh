package com.bank.root.myapplication.bean;

/**
 * Created by root on 15-3-6.
 */
public class LocalAddress {
    private double Latitude;
    private double Longitude;
    private String localAddress;

    public LocalAddress(double latitude, double longitude, String localAddress) {
        Latitude = latitude;
        Longitude = longitude;
        this.localAddress = localAddress;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public double getLatitude() {

        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public String getLocalAddress() {
        return localAddress;
    }
}
