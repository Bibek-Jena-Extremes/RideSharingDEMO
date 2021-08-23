
package com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StructuredFormatting implements Parcelable
{

    @SerializedName("main_text")
    @Expose
    private String mainText;
    @SerializedName("main_text_matched_substrings")
    @Expose
    private List<MainTextMatchedSubstring> mainTextMatchedSubstrings = null;
    @SerializedName("secondary_text")
    @Expose
    private String secondaryText;
    @SerializedName("secondary_text_matched_substrings")
    @Expose
    private List<SecondaryTextMatchedSubstring> secondaryTextMatchedSubstrings = null;
    public final static Parcelable.Creator<StructuredFormatting> CREATOR = new Creator<StructuredFormatting>() {


        @SuppressWarnings({
            "unchecked"
        })
        public StructuredFormatting createFromParcel(Parcel in) {
            return new StructuredFormatting(in);
        }

        public StructuredFormatting[] newArray(int size) {
            return (new StructuredFormatting[size]);
        }

    }
    ;

    protected StructuredFormatting(Parcel in) {
        this.mainText = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.mainTextMatchedSubstrings, (com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.MainTextMatchedSubstring.class.getClassLoader()));
        this.secondaryText = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.secondaryTextMatchedSubstrings, (com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.SecondaryTextMatchedSubstring.class.getClassLoader()));
    }

    public StructuredFormatting() {
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public List<MainTextMatchedSubstring> getMainTextMatchedSubstrings() {
        return mainTextMatchedSubstrings;
    }

    public void setMainTextMatchedSubstrings(List<MainTextMatchedSubstring> mainTextMatchedSubstrings) {
        this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public List<SecondaryTextMatchedSubstring> getSecondaryTextMatchedSubstrings() {
        return secondaryTextMatchedSubstrings;
    }

    public void setSecondaryTextMatchedSubstrings(List<SecondaryTextMatchedSubstring> secondaryTextMatchedSubstrings) {
        this.secondaryTextMatchedSubstrings = secondaryTextMatchedSubstrings;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mainText);
        dest.writeList(mainTextMatchedSubstrings);
        dest.writeValue(secondaryText);
        dest.writeList(secondaryTextMatchedSubstrings);
    }

    public int describeContents() {
        return  0;
    }

}
