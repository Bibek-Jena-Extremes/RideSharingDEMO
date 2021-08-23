
package com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Candidate implements Parcelable
{

    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("rating")
    @Expose
    private Double rating;
    public final static Parcelable.Creator<Candidate> CREATOR = new Creator<Candidate>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        public Candidate[] newArray(int size) {
            return (new Candidate[size]);
        }

    }
    ;

    protected Candidate(Parcel in) {
        this.formattedAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.geometry = ((Geometry) in.readValue((Geometry.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.openingHours = ((OpeningHours) in.readValue((OpeningHours.class.getClassLoader())));
        in.readList(this.photos, (com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil.Photo.class.getClassLoader()));
        this.rating = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Candidate() {
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(formattedAddress);
        dest.writeValue(geometry);
        dest.writeValue(name);
        dest.writeValue(openingHours);
        dest.writeList(photos);
        dest.writeValue(rating);
    }

    public int describeContents() {
        return  0;
    }

}
