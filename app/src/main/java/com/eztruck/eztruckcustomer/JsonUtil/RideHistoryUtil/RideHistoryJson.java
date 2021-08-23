
package com.eztruck.eztruckcustomer.JsonUtil.RideHistoryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RideHistoryJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("history_list")
    @Expose
    private List<HistoryList> historyList = null;
    public final static Parcelable.Creator<RideHistoryJson> CREATOR = new Creator<RideHistoryJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RideHistoryJson createFromParcel(Parcel in) {
            return new RideHistoryJson(in);
        }

        public RideHistoryJson[] newArray(int size) {
            return (new RideHistoryJson[size]);
        }

    }
    ;

    protected RideHistoryJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.historyList, (com.eztruck.eztruckcustomer.JsonUtil.RideHistoryUtil.HistoryList.class.getClassLoader()));
    }

    public RideHistoryJson() {
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

    public List<HistoryList> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryList> historyList) {
        this.historyList = historyList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(historyList);
    }

    public int describeContents() {
        return  0;
    }

}
