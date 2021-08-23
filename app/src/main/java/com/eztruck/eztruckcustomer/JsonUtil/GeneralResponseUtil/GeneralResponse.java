
package com.eztruck.eztruckcustomer.JsonUtil.GeneralResponseUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponse implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<GeneralResponse> CREATOR = new Creator<GeneralResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GeneralResponse createFromParcel(Parcel in) {
            return new GeneralResponse(in);
        }

        public GeneralResponse[] newArray(int size) {
            return (new GeneralResponse[size]);
        }

    }
    ;

    protected GeneralResponse(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public GeneralResponse() {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
