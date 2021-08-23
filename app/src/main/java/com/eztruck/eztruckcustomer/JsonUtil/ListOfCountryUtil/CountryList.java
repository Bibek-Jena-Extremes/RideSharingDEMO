
package com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city_list")
    @Expose
    private List<CityList> cityList = null;
    public final static Parcelable.Creator<CountryList> CREATOR = new Creator<CountryList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CountryList createFromParcel(Parcel in) {
            return new CountryList(in);
        }

        public CountryList[] newArray(int size) {
            return (new CountryList[size]);
        }

    }
    ;

    protected CountryList(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.cityList, (com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil.CityList.class.getClassLoader()));
    }

    public CountryList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(cityList);
    }

    public int describeContents() {
        return  0;
    }

}
