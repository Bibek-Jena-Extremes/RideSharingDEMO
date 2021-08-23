
package com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindPlaceJson implements Parcelable
{

    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates = null;
    @SerializedName("debug_log")
    @Expose
    private DebugLog debugLog;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Parcelable.Creator<FindPlaceJson> CREATOR = new Creator<FindPlaceJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FindPlaceJson createFromParcel(Parcel in) {
            return new FindPlaceJson(in);
        }

        public FindPlaceJson[] newArray(int size) {
            return (new FindPlaceJson[size]);
        }

    }
    ;

    protected FindPlaceJson(Parcel in) {
        in.readList(this.candidates, (com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil.Candidate.class.getClassLoader()));
        this.debugLog = ((DebugLog) in.readValue((DebugLog.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FindPlaceJson() {
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public DebugLog getDebugLog() {
        return debugLog;
    }

    public void setDebugLog(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(candidates);
        dest.writeValue(debugLog);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
