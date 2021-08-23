
package com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfCountryJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("country_list")
    @Expose
    private List<CountryList> countryList = null;
    public final static Parcelable.Creator<ListOfCountryJson> CREATOR = new Creator<ListOfCountryJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ListOfCountryJson createFromParcel(Parcel in) {
            return new ListOfCountryJson(in);
        }

        public ListOfCountryJson[] newArray(int size) {
            return (new ListOfCountryJson[size]);
        }

    }
    ;

    protected ListOfCountryJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.countryList, (com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil.CountryList.class.getClassLoader()));
    }

    public ListOfCountryJson() {
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

    public List<CountryList> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryList> countryList) {
        this.countryList = countryList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(countryList);
    }

    public int describeContents() {
        return  0;
    }

}
