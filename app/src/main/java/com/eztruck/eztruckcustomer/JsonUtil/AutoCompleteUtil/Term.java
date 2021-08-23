
package com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Term implements Parcelable
{

    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Parcelable.Creator<Term> CREATOR = new Creator<Term>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Term createFromParcel(Parcel in) {
            return new Term(in);
        }

        public Term[] newArray(int size) {
            return (new Term[size]);
        }

    }
    ;

    protected Term(Parcel in) {
        this.offset = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Term() {
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(offset);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
