
package com.eztruck.eztruckcustomer.JsonUtil.RideTypeList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Captain implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("ride_type_id")
    @Expose
    private String rideTypeId;
    @SerializedName("ride_type_name")
    @Expose
    private String rideTypeName;
    @SerializedName("distance")
    @Expose
    private String distance;

    public Captain() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Captain setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public Captain setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public Captain setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getRideTypeId() {
        return rideTypeId;
    }

    public void setRideTypeId(String rideTypeId) {
        this.rideTypeId = rideTypeId;
    }

    public String getRideTypeName() {
        return rideTypeName;
    }

    public void setRideTypeName(String rideTypeName) {
        this.rideTypeName = rideTypeName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.rideTypeId);
        dest.writeString(this.rideTypeName);
        dest.writeString(this.distance);
    }

    protected Captain(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.rideTypeId = in.readString();
        this.rideTypeName = in.readString();
        this.distance = in.readString();
    }

    public static final Creator<Captain> CREATOR = new Creator<Captain>() {
        @Override
        public Captain createFromParcel(Parcel source) {
            return new Captain(source);
        }

        @Override
        public Captain[] newArray(int size) {
            return new Captain[size];
        }
    };
}
