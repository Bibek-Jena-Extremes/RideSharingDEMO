
package com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecondaryTextMatchedSubstring implements Parcelable
{

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    public final static Parcelable.Creator<SecondaryTextMatchedSubstring> CREATOR = new Creator<SecondaryTextMatchedSubstring>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SecondaryTextMatchedSubstring createFromParcel(Parcel in) {
            return new SecondaryTextMatchedSubstring(in);
        }

        public SecondaryTextMatchedSubstring[] newArray(int size) {
            return (new SecondaryTextMatchedSubstring[size]);
        }

    }
    ;

    protected SecondaryTextMatchedSubstring(Parcel in) {
        this.length = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.offset = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SecondaryTextMatchedSubstring() {
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
