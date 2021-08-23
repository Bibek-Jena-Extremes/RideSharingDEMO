
package com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchedSubstring implements Parcelable
{

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    public final static Parcelable.Creator<MatchedSubstring> CREATOR = new Creator<MatchedSubstring>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MatchedSubstring createFromParcel(Parcel in) {
            return new MatchedSubstring(in);
        }

        public MatchedSubstring[] newArray(int size) {
            return (new MatchedSubstring[size]);
        }

    }
    ;

    protected MatchedSubstring(Parcel in) {
        this.length = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.offset = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public MatchedSubstring() {
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(length);
        dest.writeValue(offset);
    }

    public int describeContents() {
        return  0;
    }

}
