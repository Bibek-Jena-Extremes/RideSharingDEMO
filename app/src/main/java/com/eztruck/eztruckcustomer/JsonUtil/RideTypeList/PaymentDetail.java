
package com.eztruck.eztruckcustomer.JsonUtil.RideTypeList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetail implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("card_title")
    @Expose
    private String cardTitle;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("expiry_month")
    @Expose
    private String expiryMonth;
    @SerializedName("expiry_year")
    @Expose
    private String expiryYear;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("customer_no")
    @Expose
    private String customerNo;
    public final static Parcelable.Creator<PaymentDetail> CREATOR = new Creator<PaymentDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaymentDetail createFromParcel(Parcel in) {
            return new PaymentDetail(in);
        }

        public PaymentDetail[] newArray(int size) {
            return (new PaymentDetail[size]);
        }

    }
    ;

    protected PaymentDetail(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
        this.cardTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.cardNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.expiryMonth = ((String) in.readValue((String.class.getClassLoader())));
        this.expiryYear = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.customerNo = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PaymentDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyName);
        dest.writeValue(cardTitle);
        dest.writeValue(cardNumber);
        dest.writeValue(expiryMonth);
        dest.writeValue(expiryYear);
        dest.writeValue(paymentType);
        dest.writeValue(customerNo);
    }

    public int describeContents() {
        return  0;
    }

}
