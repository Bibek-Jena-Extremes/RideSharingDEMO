package com.eztruck.eztruckcustomer.JsonUtil.NearbyUtil;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearbyJson implements Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("next_page_token")
    @Expose
    private String nextPageToken;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public NearbyJson() {
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.htmlAttributions);
        dest.writeString(this.nextPageToken);
        dest.writeTypedList(this.results);
        dest.writeString(this.status);
    }

    protected NearbyJson(Parcel in) {
        this.htmlAttributions = new ArrayList<Object>();
        in.readList(this.htmlAttributions, Object.class.getClassLoader());
        this.nextPageToken = in.readString();
        this.results = in.createTypedArrayList(Result.CREATOR);
        this.status = in.readString();
    }

    public static final Creator<NearbyJson> CREATOR = new Creator<NearbyJson>() {
        @Override
        public NearbyJson createFromParcel(Parcel source) {
            return new NearbyJson(source);
        }

        @Override
        public NearbyJson[] newArray(int size) {
            return new NearbyJson[size];
        }
    };
}
