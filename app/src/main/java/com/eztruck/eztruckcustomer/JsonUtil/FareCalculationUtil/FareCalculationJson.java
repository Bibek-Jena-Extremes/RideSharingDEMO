
package com.eztruck.eztruckcustomer.JsonUtil.FareCalculationUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FareCalculationJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("estimatedFare")
    @Expose
    private Integer estimatedFare;
    @SerializedName("estimatedDistance")
    @Expose
    private String estimatedDistance;
    @SerializedName("estimatedDuration")
    @Expose
    private String estimatedDuration;

    public FareCalculationJson() {
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

    public Integer getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(Integer estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public String getEstimatedDistance() {
        return estimatedDistance;
    }

    public FareCalculationJson setEstimatedDistance(String estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
        return this;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public FareCalculationJson setEstimatedDuration(String estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeValue(this.estimatedFare);
        dest.writeString(this.estimatedDistance);
        dest.writeString(this.estimatedDuration);
    }

    protected FareCalculationJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.estimatedFare = (Integer) in.readValue(Integer.class.getClassLoader());
        this.estimatedDistance = in.readString();
        this.estimatedDuration = in.readString();
    }

    public static final Creator<FareCalculationJson> CREATOR = new Creator<FareCalculationJson>() {
        @Override
        public FareCalculationJson createFromParcel(Parcel source) {
            return new FareCalculationJson(source);
        }

        @Override
        public FareCalculationJson[] newArray(int size) {
            return new FareCalculationJson[size];
        }
    };
}
