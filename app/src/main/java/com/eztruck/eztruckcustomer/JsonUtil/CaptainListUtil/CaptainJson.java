
package com.eztruck.eztruckcustomer.JsonUtil.CaptainListUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptainJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("captain")
    @Expose
    private List<Captain> captain = null;
    public final static Parcelable.Creator<CaptainJson> CREATOR = new Creator<CaptainJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CaptainJson createFromParcel(Parcel in) {
            return new CaptainJson(in);
        }

        public CaptainJson[] newArray(int size) {
            return (new CaptainJson[size]);
        }

    }
    ;

    protected CaptainJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.captain, (com.eztruck.eztruckcustomer.JsonUtil.CaptainListUtil.Captain.class.getClassLoader()));
    }

    public CaptainJson() {
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

    public List<Captain> getCaptain() {
        return captain;
    }

    public void setCaptain(List<Captain> captain) {
        this.captain = captain;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(captain);
    }

    public int describeContents() {
        return  0;
    }

}
