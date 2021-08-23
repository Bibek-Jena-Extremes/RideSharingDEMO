
package com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoCompleteJson implements Parcelable
{

    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Parcelable.Creator<AutoCompleteJson> CREATOR = new Creator<AutoCompleteJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AutoCompleteJson createFromParcel(Parcel in) {
            return new AutoCompleteJson(in);
        }

        public AutoCompleteJson[] newArray(int size) {
            return (new AutoCompleteJson[size]);
        }

    }
    ;

    protected AutoCompleteJson(Parcel in) {
        in.readList(this.predictions, (com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.Prediction.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AutoCompleteJson() {
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(predictions);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
