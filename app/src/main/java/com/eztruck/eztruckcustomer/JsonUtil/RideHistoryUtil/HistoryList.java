
package com.eztruck.eztruckcustomer.JsonUtil.RideHistoryUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("captain_name")
    @Expose
    private String captainName;
    @SerializedName("captain_picture")
    @Expose
    private String captainPicture;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("car_name")
    @Expose
    private String carName;
    @SerializedName("car_colour")
    @Expose
    private String carColour;
    @SerializedName("car_number_plate")
    @Expose
    private String carNumberPlate;
    @SerializedName("ride_type_name")
    @Expose
    private String rideTypeName;
    @SerializedName("from_address")
    @Expose
    private String from_address;
    @SerializedName("from_latitude")
    @Expose
    private String fromLatitude;
    @SerializedName("from_longitude")
    @Expose
    private String fromLongitude;
    @SerializedName("to_address")
    @Expose
    private String to_address;
    @SerializedName("to_latitude")
    @Expose
    private String toLatitude;
    @SerializedName("to_longitude")
    @Expose
    private String toLongitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("trip_time")
    @Expose
    private String tripTime;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("date_created")
    @Expose
    private String date_created;

    public HistoryList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getCaptainPicture() {
        return captainPicture;
    }

    public void setCaptainPicture(String captainPicture) {
        this.captainPicture = captainPicture;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColour() {
        return carColour;
    }

    public void setCarColour(String carColour) {
        this.carColour = carColour;
    }

    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
    }

    public String getRideTypeName() {
        return rideTypeName;
    }

    public void setRideTypeName(String rideTypeName) {
        this.rideTypeName = rideTypeName;
    }

    public String getFromLatitude() {
        return fromLatitude;
    }

    public void setFromLatitude(String fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    public String getFromLongitude() {
        return fromLongitude;
    }

    public void setFromLongitude(String fromLongitude) {
        this.fromLongitude = fromLongitude;
    }

    public String getToLatitude() {
        return toLatitude;
    }

    public void setToLatitude(String toLatitude) {
        this.toLatitude = toLatitude;
    }

    public String getToLongitude() {
        return toLongitude;
    }

    public void setToLongitude(String toLongitude) {
        this.toLongitude = toLongitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getRating() {
        return rating;
    }

    public HistoryList setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getDate_created() {
        return date_created;
    }

    public HistoryList setDate_created(String date_created) {
        this.date_created = date_created;
        return this;
    }

    public String getFrom_address() {
        return from_address;
    }

    public HistoryList setFrom_address(String from_address) {
        this.from_address = from_address;
        return this;
    }

    public String getTo_address() {
        return to_address;
    }

    public HistoryList setTo_address(String to_address) {
        this.to_address = to_address;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.customerName);
        dest.writeString(this.captainName);
        dest.writeString(this.captainPicture);
        dest.writeString(this.brandName);
        dest.writeString(this.carName);
        dest.writeString(this.carColour);
        dest.writeString(this.carNumberPlate);
        dest.writeString(this.rideTypeName);
        dest.writeString(this.from_address);
        dest.writeString(this.fromLatitude);
        dest.writeString(this.fromLongitude);
        dest.writeString(this.to_address);
        dest.writeString(this.toLatitude);
        dest.writeString(this.toLongitude);
        dest.writeString(this.distance);
        dest.writeString(this.price);
        dest.writeString(this.tripTime);
        dest.writeString(this.payment);
        dest.writeString(this.status);
        dest.writeString(this.rating);
        dest.writeString(this.date_created);
    }

    protected HistoryList(Parcel in) {
        this.id = in.readString();
        this.customerName = in.readString();
        this.captainName = in.readString();
        this.captainPicture = in.readString();
        this.brandName = in.readString();
        this.carName = in.readString();
        this.carColour = in.readString();
        this.carNumberPlate = in.readString();
        this.rideTypeName = in.readString();
        this.from_address = in.readString();
        this.fromLatitude = in.readString();
        this.fromLongitude = in.readString();
        this.to_address = in.readString();
        this.toLatitude = in.readString();
        this.toLongitude = in.readString();
        this.distance = in.readString();
        this.price = in.readString();
        this.tripTime = in.readString();
        this.payment = in.readString();
        this.status = in.readString();
        this.rating = in.readString();
        this.date_created = in.readString();
    }

    public static final Creator<HistoryList> CREATOR = new Creator<HistoryList>() {
        @Override
        public HistoryList createFromParcel(Parcel source) {
            return new HistoryList(source);
        }

        @Override
        public HistoryList[] newArray(int size) {
            return new HistoryList[size];
        }
    };
}
