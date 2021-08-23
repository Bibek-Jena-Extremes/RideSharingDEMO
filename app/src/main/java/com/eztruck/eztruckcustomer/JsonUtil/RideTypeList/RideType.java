
package com.eztruck.eztruckcustomer.JsonUtil.RideTypeList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RideType implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("picture")
    @Expose
    private String picture;

    public RideType() {
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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getCategory() {
        return category;
    }

    public RideType setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.tagline);
        dest.writeString(this.category);
        dest.writeString(this.tag);
        dest.writeString(this.picture);
    }

    protected RideType(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.tagline = in.readString();
        this.category = in.readString();
        this.tag = in.readString();
        this.picture = in.readString();
    }

    public static final Creator<RideType> CREATOR = new Creator<RideType>() {
        @Override
        public RideType createFromParcel(Parcel source) {
            return new RideType(source);
        }

        @Override
        public RideType[] newArray(int size) {
            return new RideType[size];
        }
    };
}
