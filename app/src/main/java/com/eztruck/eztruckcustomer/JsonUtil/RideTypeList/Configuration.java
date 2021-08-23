
package com.eztruck.eztruckcustomer.JsonUtil.RideTypeList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuration implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currency_name")
    @Expose
    private String currencyName;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("place_id")
    @Expose
    private String place_id;

    public Configuration() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getPlace_id() {
        return place_id;
    }

    public Configuration setPlace_id(String place_id) {
        this.place_id = place_id;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.currencyName);
        dest.writeString(this.currencySymbol);
        dest.writeString(this.place_id);
    }

    protected Configuration(Parcel in) {
        this.id = in.readString();
        this.currencyName = in.readString();
        this.currencySymbol = in.readString();
        this.place_id = in.readString();
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel source) {
            return new Configuration(source);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };
}
