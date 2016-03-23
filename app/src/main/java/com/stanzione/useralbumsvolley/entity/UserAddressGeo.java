package com.stanzione.useralbumsvolley.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAddressGeo implements Serializable {

    @SerializedName("lat") private double latitude;
    @SerializedName("lng") private double longitude;

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

}
