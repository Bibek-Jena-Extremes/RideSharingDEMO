
package com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction implements Parcelable
{

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("matched_substrings")
    @Expose
    private List<MatchedSubstring> matchedSubstrings = null;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("structured_formatting")
    @Expose
    private StructuredFormatting structuredFormatting;
    @SerializedName("terms")
    @Expose
    private List<Term> terms = null;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    public final static Parcelable.Creator<Prediction> CREATOR = new Creator<Prediction>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Prediction createFromParcel(Parcel in) {
            return new Prediction(in);
        }

        public Prediction[] newArray(int size) {
            return (new Prediction[size]);
        }

    }
    ;

    protected Prediction(Parcel in) {
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.matchedSubstrings, (com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.MatchedSubstring.class.getClassLoader()));
        this.placeId = ((String) in.readValue((String.class.getClassLoader())));
        this.reference = ((String) in.readValue((String.class.getClassLoader())));
        this.structuredFormatting = ((StructuredFormatting) in.readValue((StructuredFormatting.class.getClassLoader())));
        in.readList(this.terms, (com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.Term.class.getClassLoader()));
        in.readList(this.types, (java.lang.String.class.getClassLoader()));
    }

    public Prediction() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MatchedSubstring> getMatchedSubstrings() {
        return matchedSubstrings;
    }

    public void setMatchedSubstrings(List<MatchedSubstring> matchedSubstrings) {
        this.matchedSubstrings = matchedSubstrings;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(StructuredFormatting structuredFormatting) {
        this.structuredFormatting = structuredFormatting;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(description);
        dest.writeValue(id);
        dest.writeList(matchedSubstrings);
        dest.writeValue(placeId);
        dest.writeValue(reference);
        dest.writeValue(structuredFormatting);
        dest.writeList(terms);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}
