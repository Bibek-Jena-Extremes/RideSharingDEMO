
package com.eztruck.eztruckcustomer.JsonUtil.RideTypeList;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RideTypeJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("rideType")
    @Expose
    private List<RideType> rideType = null;
    @SerializedName("paymentType")
    @Expose
    private List<PaymentType> paymentType = null;
    @SerializedName("configuration")
    @Expose
    private List<Configuration> configuration = null;
    @SerializedName("captain")
    @Expose
    private List<Captain> captain = null;
    public final static Parcelable.Creator<RideTypeJson> CREATOR = new Creator<RideTypeJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RideTypeJson createFromParcel(Parcel in) {
            return new RideTypeJson(in);
        }

        public RideTypeJson[] newArray(int size) {
            return (new RideTypeJson[size]);
        }

    }
    ;

    protected RideTypeJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.rideType, (com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.RideType.class.getClassLoader()));
        in.readList(this.paymentType, (com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.PaymentType.class.getClassLoader()));
        in.readList(this.configuration, (com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.Configuration.class.getClassLoader()));
        in.readList(this.captain, (com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.Captain.class.getClassLoader()));
    }

    public RideTypeJson() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RideType> getRideType() {
        return rideType;
    }

    public void setRideType(List<RideType> rideType) {
        this.rideType = rideType;
    }

    public List<PaymentType> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(List<PaymentType> paymentType) {
        this.paymentType = paymentType;
    }

    public List<Configuration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<Configuration> configuration) {
        this.configuration = configuration;
    }

    public List<Captain> getCaptain() {
        return captain;
    }

    public void setCaptain(List<Captain> captain) {
        this.captain = captain;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(rideType);
        dest.writeList(paymentType);
        dest.writeList(configuration);
        dest.writeList(captain);
    }

    public int describeContents() {
        return  0;
    }

}
