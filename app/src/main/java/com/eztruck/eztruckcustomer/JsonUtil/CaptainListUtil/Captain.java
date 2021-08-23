
package com.eztruck.eztruckcustomer.JsonUtil.CaptainListUtil;

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
    @SerializedName("rider_type_id")
    @Expose
    private String riderTypeId;
    @SerializedName("rider_type_name")
    @Expose
    private String riderTypeName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("duration")
    @Expose
    private String duration;
    public final static Parcelable.Creator<Captain> CREATOR = new Creator<Captain>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Captain createFromParcel(Parcel in) {
            return new Captain(in);
        }

        public Captain[] newArray(int size) {
            return (new Captain[size]);
        }

    }
    ;

    protected Captain(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.riderTypeId = ((String) in.readValue((String.class.getClassLoader())));
        this.riderTypeName = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.distance = ((String) in.readValue((String.class.getClassLoader())));
        this.duration = ((String) in.readValue((String.class.getClassLoader())));
    }

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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRiderTypeId() {
        return riderTypeId;
    }

    public void setRiderTypeId(String riderTypeId) {
        this.riderTypeId = riderTypeId;
    }

    public String getRiderTypeName() {
        return riderTypeName;
    }

    public void setRiderTypeName(String riderTypeName) {
        this.riderTypeName = riderTypeName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(phone);
        dest.writeValue(riderTypeId);
        dest.writeValue(riderTypeName);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(distance);
        dest.writeValue(duration);
    }

    public int describeContents() {
        return  0;
    }

}
