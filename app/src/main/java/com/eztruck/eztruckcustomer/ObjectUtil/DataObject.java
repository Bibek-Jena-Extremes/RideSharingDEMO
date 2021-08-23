package com.eztruck.eztruckcustomer.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.DateUtil.DateBuilder;
import com.eztruck.eztruckcustomer.DateUtil.DateConstraint;
import com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.AutoCompleteJson;
import com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.Prediction;
import com.eztruck.eztruckcustomer.JsonUtil.CaptainListUtil.CaptainJson;
import com.eztruck.eztruckcustomer.JsonUtil.CardUtil.Card;
import com.eztruck.eztruckcustomer.JsonUtil.CardUtil.CardJson;
import com.eztruck.eztruckcustomer.JsonUtil.CouponUtil.CouponJson;
import com.eztruck.eztruckcustomer.JsonUtil.FareCalculationUtil.FareCalculationJson;
import com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil.Candidate;
import com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil.FindPlaceJson;
import com.eztruck.eztruckcustomer.JsonUtil.GeneralResponseUtil.GeneralResponse;
import com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil.CityList;
import com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil.CountryList;
import com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil.ListOfCountryJson;
import com.eztruck.eztruckcustomer.JsonUtil.NearbyUtil.NearbyJson;
import com.eztruck.eztruckcustomer.JsonUtil.NearbyUtil.Result;
import com.eztruck.eztruckcustomer.JsonUtil.OrderUtil.OrderJson;
import com.eztruck.eztruckcustomer.JsonUtil.PrivacyPolicyUtil.PrivacyPolicyJson;
import com.eztruck.eztruckcustomer.JsonUtil.RideHistoryUtil.HistoryList;
import com.eztruck.eztruckcustomer.JsonUtil.RideHistoryUtil.RideHistoryJson;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.Captain;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.Configuration;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.PaymentDetail;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.PaymentType;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.RideType;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.RideTypeJson;
import com.eztruck.eztruckcustomer.JsonUtil.UserUtil.FavouriteList;
import com.eztruck.eztruckcustomer.JsonUtil.UserUtil.UserJson;

import java.util.ArrayList;

public class DataObject implements Parcelable {

    /* Variable for Connection Builder */

    private String code;
    private String message;

    /* Variable for Debugging */

    private static String TAG = DataObject.class.getName();


    /* Variable for UserObject */

    private String user_id;
    private String user_fName;
    private String user_lName;
    private String user_password;
    private String login_type;
    private String user_picture;
    private String user_phone;
    private String user_email;
    private String user_sign_in;
    private String user_remember;
    private ArrayList<FavouriteList> userFavourite = new ArrayList<>();
    private String user_payment_type;


    /* Variable for For Ride Type */

    private String ride_type_id;
    private String ride_type;
    private String ride_type_name;
    private String ride_type_tagline;
    private String ride_type_category;
    private String ride_type_image;
    private boolean selected_ride_type;

    /* Variable for For Recent Location */

    private String location_label;
    private String location_name;
    private String location_Tagline;
    private double location_latitude;
    private double location_longitude;
    private boolean locationSelected;

    private String country_id;
    private String country_name;
    private String city_name;
    private String city_id;
    private String city_latitude;
    private String city_longitude;

    /* Variable for For Payment Type */

    private String payment_type_id;
    private String payment_type_name;
    private String payment_type;
    private String payment_type_picture;
    private boolean selected_payment_type;
    private boolean selected_payment;


    /* Variable for For App Configuration */

    private String currency_symbol;
    private String currency_name;
    private String fare_estimation;
    private String distance_estimation;
    private String duration_estimation;
    private String place_id;

    /* Variable for payments */

    private String payment_id;
    private String payment_card_no;
    private String payment_expiry_date;
    private String stripe_customer_no;
    private String payee_name;
    private String payment_card_company;

    /* Variable for Captain */

    private String captain_duration;
    private String captain_id;
    private String captain_name;
    private String captain_phone;
    private String captain_latitude;
    private String captain_longitude;
    private String captain_ride_type_id;
    private String captain_ride_type_name;
    private String captain_distance;

    /* Variable for History */

    private String history_from_address;
    private String history_from_latitude;
    private String history_from_longitude;
    private String history_to_address;
    private String history_to_latitude;
    private String history_to_longitude;
    private String ride_date_time;
    private String ride_fare;
    private String ride_payment_type;
    private String ride_distance;
    private String ride_duration;
    private String ride_captain_name;
    private String ride_captain_picture;
    private String ride_captain_ride_type;
    private String ride_captain_car_brand;
    private String ride_captain_car_name;
    private String ride_captain_car_colour;
    private String ride_captain_number_plate;



    /* Variable for  Detail */

    private String object_id;
    private String object_min_delivery_time;
    private String object_min_order_price;
    private String object_delivery_charges;
    private String object_currency_symbol;
    private String object_latitude;
    private String object_longitude;
    private String paymentType;
    private String day;
    private String fromTime;
    private String toTime;


    /* Variable for Review of Object */

    private String rating;
    private ArrayList<String> reviewPictureList = new ArrayList<>();

    /* Variable for Coupons */

    private String coupon_id;
    private String coupon_reward;


    /* Variable for Restaurant Menu */

    private String post_id;
    private String post_name;
    private String post_price;
    private String post_quantity;
    private String post_attribute_id;
    private String special_instructions;


    /* Variable for Order History */

    private String order_id;


    /* Variable for Data Type */

    private Constant.DATA_TYPE dataType;
    private ArrayList<DataObject> objectArrayList = new ArrayList<>();
    private ArrayList<DataObject> captainArraylist = new ArrayList<>();
    private ArrayList<Object> homeList = new ArrayList<>();

    /* Variable for Favourites */

    private String favourite_id;
    private String cart_id;


    /* Variable for Chatting */

    private String chatting;
    private String picture;
    private String date;
    private String time;
    private boolean isFrom;

    /* General Variable  */

    private String basePrice;
    private boolean fromSplash;


    private String privacy_policy;



    public String getCode() {
        return code;
    }

    public DataObject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DataObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public DataObject setPayee_name(String payee_name) {
        this.payee_name = payee_name;
        return this;
    }

    public String getPayment_card_company() {
        return payment_card_company;
    }

    public DataObject setPayment_card_company(String payment_card_company) {
        this.payment_card_company = payment_card_company;
        return this;
    }

    public String getUser_id() {
        return user_id;
    }

    public DataObject setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getUser_fName() {
        return user_fName;
    }

    public DataObject setUser_fName(String user_fName) {
        this.user_fName = user_fName;
        return this;
    }

    public String getUser_lName() {
        return user_lName;
    }

    public DataObject setUser_lName(String user_lName) {
        this.user_lName = user_lName;
        return this;
    }

    public String getUser_password() {
        return user_password;
    }

    public DataObject setUser_password(String user_password) {
        this.user_password = user_password;
        return this;
    }

    public String getLogin_type() {
        return login_type;
    }

    public DataObject setLogin_type(String login_type) {
        this.login_type = login_type;
        return this;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public DataObject setUser_picture(String user_picture) {
        this.user_picture = user_picture;
        return this;
    }

    public String getPhone() {
        return user_phone;
    }

    public DataObject setPhone(String phone) {
        this.user_phone = phone;
        return this;
    }

    public String getUser_email() {
        return user_email;
    }

    public DataObject setUser_email(String user_email) {
        this.user_email = user_email;
        return this;
    }

    public ArrayList<FavouriteList> getUserFavourite() {
        return userFavourite;
    }

    public DataObject setUserFavourite(ArrayList<FavouriteList> userFavourite) {
        this.userFavourite = userFavourite;
        return this;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public DataObject setPayment_id(String payment_id) {
        this.payment_id = payment_id;
        return this;
    }

    public String getRide_type_id() {
        return ride_type_id;
    }

    public DataObject setRide_type_id(String ride_type_id) {
        this.ride_type_id = ride_type_id;
        return this;
    }

    public String getRide_type() {
        return ride_type;
    }

    public DataObject setRide_type(String ride_type) {
        this.ride_type = ride_type;
        return this;
    }

    public String getRide_type_name() {
        return ride_type_name;
    }

    public DataObject setRide_type_name(String ride_type_name) {
        this.ride_type_name = ride_type_name;
        return this;
    }

    public String getRide_type_tagline() {
        return ride_type_tagline;
    }

    public DataObject setRide_type_tagline(String ride_type_tagline) {
        this.ride_type_tagline = ride_type_tagline;
        return this;
    }

    public String getRide_type_category() {
        return ride_type_category;
    }

    public DataObject setRide_type_category(String ride_type_category) {
        this.ride_type_category = ride_type_category;
        return this;
    }

    public String getRide_type_image() {
        return ride_type_image;
    }

    public DataObject setRide_type_image(String ride_type_image) {
        this.ride_type_image = ride_type_image;
        return this;
    }

    public boolean isSelected_ride_type() {
        return selected_ride_type;
    }

    public DataObject setSelected_ride_type(boolean selected_ride_type) {
        this.selected_ride_type = selected_ride_type;
        return this;
    }

    public String getLocation_label() {
        return location_label;
    }

    public DataObject setLocation_label(String location_label) {
        this.location_label = location_label;
        return this;
    }

    public String getLocation_name() {
        return location_name;
    }

    public DataObject setLocation_name(String location_name) {
        this.location_name = location_name;
        return this;
    }

    public String getLocation_Tagline() {
        return location_Tagline;
    }

    public DataObject setLocation_Tagline(String location_Tagline) {
        this.location_Tagline = location_Tagline;
        return this;
    }


    public String getCountry_id() {
        return country_id;
    }

    public DataObject setCountry_id(String country_id) {
        this.country_id = country_id;
        return this;
    }

    public String getCountry_name() {
        return country_name;
    }

    public DataObject setCountry_name(String country_name) {
        this.country_name = country_name;
        return this;
    }


    public String getCity_name() {
        return city_name;
    }

    public DataObject setCity_name(String city_name) {
        this.city_name = city_name;
        return this;
    }

    public String getCity_id() {
        return city_id;
    }

    public DataObject setCity_id(String city_id) {
        this.city_id = city_id;
        return this;
    }

    public String getCity_latitude() {
        return city_latitude;
    }

    public DataObject setCity_latitude(String city_latitude) {
        this.city_latitude = city_latitude;
        return this;
    }

    public String getCity_longitude() {
        return city_longitude;
    }

    public DataObject setCity_longitude(String city_longitude) {
        this.city_longitude = city_longitude;
        return this;
    }

    public double getLocation_latitude() {
        return location_latitude;
    }

    public DataObject setLocation_latitude(double location_latitude) {
        this.location_latitude = location_latitude;
        return this;
    }

    public double getLocation_longitude() {
        return location_longitude;
    }

    public DataObject setLocation_longitude(double location_longitude) {
        this.location_longitude = location_longitude;
        return this;
    }

    public boolean isLocationSelected() {
        return locationSelected;
    }

    public DataObject setLocationSelected(boolean locationSelected) {
        this.locationSelected = locationSelected;
        return this;
    }

    public String getPayment_type_id() {
        return payment_type_id;
    }

    public DataObject setPayment_type_id(String payment_type_id) {
        this.payment_type_id = payment_type_id;
        return this;
    }

    public String getPayment_type_name() {
        return payment_type_name;
    }

    public DataObject setPayment_type_name(String payment_type_name) {
        this.payment_type_name = payment_type_name;
        return this;
    }

    public boolean isSelected_payment_type() {
        return selected_payment_type;
    }

    public DataObject setSelected_payment_type(boolean selected_payment_type) {
        this.selected_payment_type = selected_payment_type;
        return this;
    }

    public boolean isSelected_payment() {
        return selected_payment;
    }

    public DataObject setSelected_payment(boolean selected_payment) {
        this.selected_payment = selected_payment;
        return this;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public DataObject setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
        return this;
    }


    public String getPlace_id() {
        return place_id;
    }

    public DataObject setPlace_id(String place_id) {
        this.place_id = place_id;
        return this;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public DataObject setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
        return this;
    }

    public String getFare_estimation() {
        return fare_estimation;
    }

    public DataObject setFare_estimation(String fare_estimation) {
        this.fare_estimation = fare_estimation;
        return this;
    }

    public String getDistance_estimation() {
        return distance_estimation;
    }

    public DataObject setDistance_estimation(String distance_estimation) {
        this.distance_estimation = distance_estimation;
        return this;
    }

    public String getDuration_estimation() {
        return duration_estimation;
    }

    public DataObject setDuration_estimation(String duration_estimation) {
        this.duration_estimation = duration_estimation;
        return this;
    }

    public String getPayment_card_no() {
        return payment_card_no;
    }

    public DataObject setPayment_card_no(String payment_card_no) {
        this.payment_card_no = payment_card_no;
        return this;
    }

    public String getPayment_expiry_date() {
        return payment_expiry_date;
    }

    public DataObject setPayment_expiry_date(String payment_expiry_date) {
        this.payment_expiry_date = payment_expiry_date;
        return this;
    }

    public String getStripe_customer_no() {
        return stripe_customer_no;
    }

    public DataObject setStripe_customer_no(String stripe_customer_no) {
        this.stripe_customer_no = stripe_customer_no;
        return this;
    }


    public String getCaptain_duration() {
        return captain_duration;
    }

    public DataObject setCaptain_duration(String captain_duration) {
        this.captain_duration = captain_duration;
        return this;
    }

    public String getCaptain_id() {
        return captain_id;
    }

    public DataObject setCaptain_id(String captain_id) {
        this.captain_id = captain_id;
        return this;
    }

    public String getCaptain_name() {
        return captain_name;
    }

    public DataObject setCaptain_name(String captain_name) {
        this.captain_name = captain_name;
        return this;
    }

    public String getCaptain_phone() {
        return captain_phone;
    }

    public DataObject setCaptain_phone(String captain_phone) {
        this.captain_phone = captain_phone;
        return this;
    }

    public String getCaptain_latitude() {
        return captain_latitude;
    }

    public DataObject setCaptain_latitude(String captain_latitude) {
        this.captain_latitude = captain_latitude;
        return this;
    }

    public String getCaptain_longitude() {
        return captain_longitude;
    }

    public DataObject setCaptain_longitude(String captain_longitude) {
        this.captain_longitude = captain_longitude;
        return this;
    }

    public String getCaptain_ride_type_id() {
        return captain_ride_type_id;
    }

    public DataObject setCaptain_ride_type_id(String captain_ride_type_id) {
        this.captain_ride_type_id = captain_ride_type_id;
        return this;
    }

    public String getCaptain_ride_type_name() {
        return captain_ride_type_name;
    }

    public DataObject setCaptain_ride_type_name(String captain_ride_type_name) {
        this.captain_ride_type_name = captain_ride_type_name;
        return this;
    }

    public String getCaptain_distance() {
        return captain_distance;
    }

    public DataObject setCaptain_distance(String captain_distance) {
        this.captain_distance = captain_distance;
        return this;
    }

    public String getRide_captain_picture() {
        return ride_captain_picture;
    }

    public String getHistory_from_address() {
        return history_from_address;
    }

    public DataObject setHistory_from_address(String history_from_address) {
        this.history_from_address = history_from_address;
        return this;
    }

    public String getHistory_from_latitude() {
        return history_from_latitude;
    }

    public DataObject setHistory_from_latitude(String history_from_latitude) {
        this.history_from_latitude = history_from_latitude;
        return this;
    }

    public String getHistory_from_longitude() {
        return history_from_longitude;
    }

    public DataObject setHistory_from_longitude(String history_from_longitude) {
        this.history_from_longitude = history_from_longitude;
        return this;
    }

    public String getHistory_to_latitude() {
        return history_to_latitude;
    }

    public DataObject setHistory_to_latitude(String history_to_latitude) {
        this.history_to_latitude = history_to_latitude;
        return this;
    }

    public String getHistory_to_longitude() {
        return history_to_longitude;
    }

    public DataObject setHistory_to_longitude(String history_to_longitude) {
        this.history_to_longitude = history_to_longitude;
        return this;
    }

    public String getHistory_to_address() {
        return history_to_address;
    }

    public DataObject setHistory_to_address(String history_to_address) {
        this.history_to_address = history_to_address;
        return this;
    }

    public String getRide_date_time() {
        return ride_date_time;
    }

    public DataObject setRide_date_time(String ride_date_time) {
        this.ride_date_time = ride_date_time;
        return this;
    }

    public String getRide_fare() {
        return ride_fare;
    }

    public DataObject setRide_fare(String ride_fare) {
        this.ride_fare = ride_fare;
        return this;
    }

    public String getRide_payment_type() {
        return ride_payment_type;
    }

    public DataObject setRide_payment_type(String ride_payment_type) {
        this.ride_payment_type = ride_payment_type;
        return this;
    }

    public String getRide_distance() {
        return ride_distance;
    }

    public DataObject setRide_distance(String ride_distance) {
        this.ride_distance = ride_distance;
        return this;
    }

    public String getRide_duration() {
        return ride_duration;
    }

    public DataObject setRide_duration(String ride_duration) {
        this.ride_duration = ride_duration;
        return this;
    }

    public String getRide_captain_name() {
        return ride_captain_name;
    }

    public DataObject setRide_captain_name(String ride_captain_name) {
        this.ride_captain_name = ride_captain_name;
        return this;
    }


    public DataObject setRide_captain_picture(String ride_captain_picture) {
        this.ride_captain_picture = ride_captain_picture;
        return this;
    }

    public String getRide_captain_ride_type() {
        return ride_captain_ride_type;
    }

    public DataObject setRide_captain_ride_type(String ride_captain_ride_type) {
        this.ride_captain_ride_type = ride_captain_ride_type;
        return this;
    }

    public String getRide_captain_car_brand() {
        return ride_captain_car_brand;
    }

    public DataObject setRide_captain_car_brand(String ride_captain_car_brand) {
        this.ride_captain_car_brand = ride_captain_car_brand;
        return this;
    }

    public String getRide_captain_car_name() {
        return ride_captain_car_name;
    }

    public DataObject setRide_captain_car_name(String ride_captain_car_name) {
        this.ride_captain_car_name = ride_captain_car_name;
        return this;
    }

    public String getRide_captain_car_colour() {
        return ride_captain_car_colour;
    }

    public DataObject setRide_captain_car_colour(String ride_captain_car_colour) {
        this.ride_captain_car_colour = ride_captain_car_colour;
        return this;
    }

    public String getRide_captain_number_plate() {
        return ride_captain_number_plate;
    }

    public DataObject setRide_captain_number_plate(String ride_captain_number_plate) {
        this.ride_captain_number_plate = ride_captain_number_plate;
        return this;
    }

    public String getObject_id() {
        return object_id;
    }

    public DataObject setObject_id(String object_id) {
        this.object_id = object_id;
        return this;
    }

    public String getObject_min_delivery_time() {
        return object_min_delivery_time;
    }

    public DataObject setObject_min_delivery_time(String object_min_delivery_time) {
        this.object_min_delivery_time = object_min_delivery_time;
        return this;
    }

    public String getObject_min_order_price() {
        return object_min_order_price;
    }

    public DataObject setObject_min_order_price(String object_min_order_price) {
        this.object_min_order_price = object_min_order_price;
        return this;
    }

    public String getObject_delivery_charges() {
        return object_delivery_charges;
    }

    public DataObject setObject_delivery_charges(String object_delivery_charges) {
        this.object_delivery_charges = object_delivery_charges;
        return this;
    }

    public String getObject_currency_symbol() {
        return object_currency_symbol;
    }

    public DataObject setObject_currency_symbol(String object_currency_symbol) {
        this.object_currency_symbol = object_currency_symbol;
        return this;
    }

    public String getObject_latitude() {
        return object_latitude;
    }

    public DataObject setObject_latitude(String object_latitude) {
        this.object_latitude = object_latitude;
        return this;
    }

    public String getObject_longitude() {
        return object_longitude;
    }

    public DataObject setObject_longitude(String object_longitude) {
        this.object_longitude = object_longitude;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public DataObject setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getDay() {
        return day;
    }

    public DataObject setDay(String day) {
        this.day = day;
        return this;
    }

    public String getFromTime() {
        return fromTime;
    }

    public DataObject setFromTime(String fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public String getToTime() {
        return toTime;
    }

    public DataObject setToTime(String toTime) {
        this.toTime = toTime;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public DataObject setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public ArrayList<String> getReviewPictureList() {
        return reviewPictureList;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public DataObject setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
        return this;
    }

    public String getCoupon_reward() {
        return coupon_reward;
    }

    public DataObject setCoupon_reward(String coupon_reward) {
        this.coupon_reward = coupon_reward;
        return this;
    }

    public String getPost_id() {
        return post_id;
    }

    public DataObject setPost_id(String post_id) {
        this.post_id = post_id;
        return this;
    }

    public String getPost_name() {
        return post_name;
    }

    public DataObject setPost_name(String post_name) {
        this.post_name = post_name;
        return this;
    }

    public String getPost_price() {
        return post_price;
    }

    public DataObject setPost_price(String post_price) {
        this.post_price = post_price;
        return this;
    }

    public String getPost_quantity() {
        return post_quantity;
    }

    public DataObject setPost_quantity(String post_quantity) {
        this.post_quantity = post_quantity;
        return this;
    }

    public String getPost_attribute_id() {
        return post_attribute_id;
    }

    public DataObject setPost_attribute_id(String post_attribute_id) {
        this.post_attribute_id = post_attribute_id;
        return this;
    }

    public String getSpecial_instructions() {
        return special_instructions;
    }

    public DataObject setSpecial_instructions(String special_instructions) {
        this.special_instructions = special_instructions;
        return this;
    }

    public Constant.DATA_TYPE getDataType() {
        return dataType;
    }

    public DataObject setDataType(Constant.DATA_TYPE dataType) {
        this.dataType = dataType;
        return this;
    }

    public ArrayList<DataObject> getCaptainArraylist() {
        return captainArraylist;
    }

    public DataObject setCaptainArraylist(ArrayList<DataObject> captainArraylist) {
        this.captainArraylist = captainArraylist;
        return this;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public DataObject setBasePrice(String basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public boolean isFromSplash() {
        return fromSplash;
    }

    public DataObject setFromSplash(boolean fromSplash) {
        this.fromSplash = fromSplash;
        return this;
    }


    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public DataObject setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
        return this;
    }

    public ArrayList<DataObject> getObjectArrayList() {
        return objectArrayList;
    }

    public DataObject setObjectArrayList(ArrayList<DataObject> objectArrayList) {
        this.objectArrayList = objectArrayList;
        return this;
    }

    public ArrayList<Object> getHomeList() {
        return homeList;
    }

    public DataObject setHomeList(ArrayList<Object> homeList) {
        this.homeList = homeList;
        return this;
    }

    public String getFavourite_id() {
        return favourite_id;
    }

    public DataObject setFavourite_id(String favourite_id) {
        this.favourite_id = favourite_id;
        return this;
    }

    public String getCart_id() {
        return cart_id;
    }

    public DataObject setCart_id(String cart_id) {
        this.cart_id = cart_id;
        return this;
    }

    public String getChatting() {
        return chatting;
    }

    public DataObject setChatting(String chatting) {
        this.chatting = chatting;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public DataObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DataObject setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public DataObject setTime(String time) {
        this.time = time;
        return this;
    }

    public boolean isFrom() {
        return isFrom;
    }

    public DataObject setFrom(boolean from) {
        isFrom = from;
        return this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public DataObject setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public DataObject setPayment_type(String payment_type) {
        this.payment_type = payment_type;
        return this;
    }

    public String getPayment_type_picture() {
        return payment_type_picture;
    }

    public DataObject setPayment_type_picture(String payment_type_picture) {
        this.payment_type_picture = payment_type_picture;
        return this;
    }


    public static DataObject getDataObject(RequestObject requestObject, Object data) {

        DataObject dataObject = null;
        String nextPage = null;

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_RIDE_PAYMENT_TYPE) {

            //region Home Functionality

            ArrayList<DataObject> rideList = new ArrayList<>();
            ArrayList<Object> paymentList = new ArrayList<>();
            ArrayList<DataObject> captainList = new ArrayList<>();
            RideTypeJson rideTypeJson = (RideTypeJson) data;


            /*List of Ride Type*/

            for (int i = 0; i < rideTypeJson.getRideType().size(); i++) {

                RideType rideType = rideTypeJson.getRideType().get(i);
                rideList.add(new DataObject()
                        .setRide_type_id(rideType.getId())
                        .setRide_type_name(rideType.getName())
                        .setRide_type_tagline(rideType.getTagline())
                        .setRide_type_category(rideType.getCategory())
                        .setRide_type(rideType.getTag())
                        .setRide_type_image(rideType.getPicture())
                        .setDataType(Constant.DATA_TYPE.RIDE_TYPE));

            }

            /*List of Payment Type*/

            for (int i = 0; i < rideTypeJson.getPaymentType().size(); i++) {

                PaymentType paymentType = rideTypeJson.getPaymentType().get(i);

                boolean selectionType = false;

                selectionType = paymentType.getPaymentDetail() == null
                        || paymentType.getPaymentDetail().size() <= 0;

                paymentList.add(new DataObject()
                        .setPayment_type_id(paymentType.getId())
                        .setPayment_type_name(paymentType.getName())
                        .setPayment_type(paymentType.getTag())
                        .setPayment_type_picture(paymentType.getPicture())
                        .setSelected_payment_type(selectionType)
                        .setDataType(Constant.DATA_TYPE.CARD_TYPE));

                if (paymentType.getPaymentDetail() != null
                        && paymentType.getPaymentDetail().size() > 0) {

                    for (int j = 0; j < paymentType.getPaymentDetail().size(); j++) {

                        PaymentDetail paymentDetail = paymentType.getPaymentDetail().get(j);
                        paymentList.add(new DataObject()
                                .setPayment_id(paymentDetail.getId())
                                .setPayment_type(paymentDetail.getId())
                                .setPayee_name(paymentDetail.getCardTitle())
                                .setPayment_card_company(paymentDetail.getCompanyName())
                                .setPayment_card_no(paymentDetail.getCardNumber())
                                .setPayment_expiry_date(paymentDetail.getExpiryMonth() + "/" + paymentDetail.getExpiryYear())
                                .setStripe_customer_no(paymentDetail.getCustomerNo())
                                .setPayment_type_picture(paymentType.getPicture())
                                .setDataType(Constant.DATA_TYPE.CARD_DETAIL));

                    }
                }

            }

            /*List of Nearest Captain*/

            for (int i = 0; i < rideTypeJson.getCaptain().size(); i++) {

                Captain captain = rideTypeJson.getCaptain().get(i);
                captainList.add(new DataObject()
                        .setCaptain_id(captain.getId())
                        .setCaptain_name(captain.getName())
                        .setCaptain_phone(captain.getPhone())
                        .setCaptain_latitude(captain.getLatitude())
                        .setCaptain_longitude(captain.getLongitude())
                        .setCaptain_ride_type_id(captain.getRideTypeId())
                        .setCaptain_ride_type_name(captain.getRideTypeName())
                        .setCaptain_distance(captain.getDistance()));


            }

            Configuration configuration;

            if (rideTypeJson.getConfiguration().size()>0)
               configuration = rideTypeJson.getConfiguration().get(0);
            else
                configuration = new Configuration();

            //endregion

            dataObject = new DataObject()
                    .setCode(rideTypeJson.getCode())
                    .setMessage(rideTypeJson.getMessage())
                    .setCurrency_name(configuration.getCurrencyName())
                    .setCurrency_symbol(configuration.getCurrencySymbol())
                    .setPlace_id(configuration.getPlace_id())
                    .setObjectArrayList(rideList)
                    .setHomeList(paymentList)
                    .setCaptainArraylist(captainList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_PICK_UP_TIME) {

            //region Estimated PickUp Time

            ArrayList<DataObject> captainList = new ArrayList<>();
            CaptainJson captainJson = (CaptainJson) data;

            /*List of Captain*/

            for (int i = 0; i < captainJson.getCaptain().size(); i++) {

                com.eztruck.eztruckcustomer.JsonUtil.CaptainListUtil.Captain captain = captainJson.getCaptain().get(i);
                captainList.add(new DataObject()
                        .setCaptain_id(captain.getId())
                        .setCaptain_name(captain.getName())
                        .setCaptain_phone(captain.getPhone())
                        .setCaptain_latitude(captain.getLatitude())
                        .setCaptain_longitude(captain.getLongitude())
                        .setCaptain_ride_type_id(captain.getRiderTypeId())
                        .setCaptain_ride_type_name(captain.getRiderTypeName())
                        .setCaptain_distance(captain.getDistance())
                        .setCaptain_duration(captain.getDuration()));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(captainJson.getCode())
                    .setMessage(captainJson.getMessage())
                    .setObjectArrayList(captainList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_FARE_PRICE) {

            //region Estimated PickUp Time

            FareCalculationJson fareCalculationJson = (FareCalculationJson) data;

            //endregion

            dataObject = new DataObject()
                    .setCode(fareCalculationJson.getCode())
                    .setMessage(fareCalculationJson.getMessage())
                    .setDistance_estimation(String.valueOf(fareCalculationJson.getEstimatedDistance()))
                    .setFare_estimation(String.valueOf(fareCalculationJson.getEstimatedFare()))
                    .setDuration_estimation(fareCalculationJson.getEstimatedDuration());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.LIST_OF_CITY) {

            //region Country List

            ArrayList<DataObject> listOfCountryArraylist = new ArrayList<>();
            ListOfCountryJson listOfCountryJson = (ListOfCountryJson) data;

            /*List of City */

            for (int i = 0; i < listOfCountryJson.getCountryList().size(); i++) {

                CountryList countryList = listOfCountryJson.getCountryList().get(i);
                listOfCountryArraylist.add(new DataObject()
                        .setCountry_id(countryList.getId())
                        .setCountry_name(countryList.getName())
                        .setLocation_label(countryList.getName())
                        .setDataType(Constant.DATA_TYPE.LABEL_VIEW));

                for (int j = 0; j < countryList.getCityList().size(); j++) {

                    CityList cityList = countryList.getCityList().get(j);
                    listOfCountryArraylist.add(new DataObject()
                            .setCity_id(cityList.getId())
                            .setCountry_name(countryList.getName())
                            .setCity_name(cityList.getName())
                            .setCity_latitude(cityList.getLatitude())
                            .setCity_longitude(cityList.getLongitude())
                            .setDataType(Constant.DATA_TYPE.CITY_VIEW));

                }

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(listOfCountryJson.getCode())
                    .setMessage(listOfCountryJson.getMessage())
                    .setObjectArrayList(listOfCountryArraylist);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON) {

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            CouponJson couponJson = (CouponJson) data;


            dataObject = new DataObject()
                    .setCode(couponJson.getCode())
                    .setMessage(couponJson.getMessage())
                    .setCoupon_reward(couponJson.getCouponReward())
                    .setCoupon_id(couponJson.getCouponId())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.NEARBY_LOCATIONS) {

            ArrayList<DataObject> nearestPOI = new ArrayList<>();
            NearbyJson nearbyJson = (NearbyJson) data;

            String code = null;
            String message = null;

            if (nearbyJson.getStatus().equalsIgnoreCase("Ok")) {

                code = Constant.ErrorCodes.success_code;
                message = "Load Successfully";

                for (int i = 0; i < nearbyJson.getResults().size(); i++) {

                    Result result = nearbyJson.getResults().get(i);
                    nearestPOI.add(new DataObject()
                            .setLocation_name(result.getName())
                            .setLocation_Tagline(result.getName())
                            .setLocation_latitude(result.getGeometry().getLocation().getLat())
                            .setLocation_longitude(result.getGeometry().getLocation().getLng())
                            .setDataType(Constant.DATA_TYPE.LOCATION_SELECTOR_VIEW));

                }


            } else {

                code = Constant.ErrorCodes.error_code;
                message = "Failed to load";

            }

            dataObject = new DataObject()
                    .setCode(code)
                    .setMessage(message)
                    .setObjectArrayList(nearestPOI);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.SEARCH_LOCATION) {

            ArrayList<DataObject> nearestPOI = new ArrayList<>();
            FindPlaceJson findPlaceJson = (FindPlaceJson) data;

            String code = null;
            String message = null;

            if (findPlaceJson.getStatus().equalsIgnoreCase("Ok")) {

                code = Constant.ErrorCodes.success_code;
                message = "Load Successfully";

                for (int i = 0; i < findPlaceJson.getCandidates().size(); i++) {

                    Candidate result = findPlaceJson.getCandidates().get(i);
                    nearestPOI.add(new DataObject()
                            .setLocation_name(result.getName())
                            .setLocation_Tagline(result.getFormattedAddress())
                            .setLocation_latitude(result.getGeometry().getLocation().getLat())
                            .setLocation_longitude(result.getGeometry().getLocation().getLng())
                            .setDataType(Constant.DATA_TYPE.LOCATION_SELECTOR_VIEW));

                }


            } else {

                code = Constant.ErrorCodes.error_code;
                message = "Failed to load";

            }

            dataObject = new DataObject()
                    .setCode(code)
                    .setMessage(message)
                    .setObjectArrayList(nearestPOI);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.AUTO_COMPLETE) {

            ArrayList<DataObject> nearestPOI = new ArrayList<>();
            AutoCompleteJson autoCompleteJson = (AutoCompleteJson) data;

            String code = null;
            String message = null;

            if (autoCompleteJson.getStatus().equalsIgnoreCase("Ok")) {

                code = Constant.ErrorCodes.success_code;
                message = "Load Successfully";

                for (int i = 0; i < autoCompleteJson.getPredictions().size(); i++) {

                    Prediction result = autoCompleteJson.getPredictions().get(i);
                    nearestPOI.add(new DataObject()
                            .setPlace_id(result.getPlaceId())
                            .setLocation_name(result.getStructuredFormatting().getMainText())
                            .setLocation_Tagline(result.getDescription())
                            .setDataType(Constant.DATA_TYPE.AUTO_COMPLETE_VIEW));

                }


            } else {

                code = Constant.ErrorCodes.error_code;
                message = "Failed to load";

            }

            dataObject = new DataObject()
                    .setCode(code)
                    .setMessage(message)
                    .setObjectArrayList(nearestPOI);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS
                || requestObject.getConnection() == Constant.CONNECTION.ADD_CARD) {

            ArrayList<DataObject> cardList = new ArrayList<>();
            CardJson cardJson = (CardJson) data;

            for (int i = 0; i < cardJson.getCards().size(); i++) {
                Card card = cardJson.getCards().get(i);
                cardList.add(new DataObject()
                        .setPayment_id(card.getId())
                        .setPayment_type(card.getId())
                        .setPayment_card_company(card.getCardName())
                        .setPayment_card_no(card.getCardNo())
                        .setPayment_expiry_date(card.getExpiry_month() + "/" + card.getExpiry_year())
                        .setStripe_customer_no(card.getStripeCustomerId())
                        .setDataType(Constant.DATA_TYPE.CARD_DETAIL));

            }

            dataObject = new DataObject()
                    .setCode(cardJson.getCode())
                    .setMessage(cardJson.getMessage())
                    .setObjectArrayList(cardList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.BOOK_RIDE) {

            //region Check out

            OrderJson orderJson = (OrderJson) data;
            dataObject = new DataObject()
                    .setCode(orderJson.getCode())
                    .setMessage(orderJson.getMessage())
                    .setOrder_id(orderJson.getOrder_id());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY) {

            //region Retrieve Order

            ArrayList<DataObject> rideHistoryList = new ArrayList<>();
            RideHistoryJson rideHistoryJson = (RideHistoryJson) data;

            /*List of Ride History List */

            for (int i = 0; i < rideHistoryJson.getHistoryList().size(); i++) {

                HistoryList historyList = rideHistoryJson.getHistoryList().get(i);

                String dateTime = new DateBuilder()
                        .setDateTimAction(DateConstraint.DateTimAction.FIND_DATE_TIME)
                        .setDateTimeConstraint(DateConstraint.DateTimeConstraint.PARSE_DATE)
                        .setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint.dd_mm_yyyy_h_mm_a)
                        .setGivenDateTime(historyList.getDate_created())
                        .setCustomFormat("yyyy-MM-dd hh:mm a")
                        .buildDateTime();

                rideHistoryList.add(new DataObject()
                        .setOrder_id(historyList.getId())
                        .setHistory_from_address(historyList.getFrom_address())
                        .setHistory_from_latitude(historyList.getFromLatitude())
                        .setHistory_from_longitude(historyList.getFromLongitude())
                        .setHistory_to_address(historyList.getTo_address())
                        .setHistory_to_latitude(historyList.getToLatitude())
                        .setHistory_to_longitude(historyList.getToLongitude())
                        .setRide_date_time(dateTime)
                        .setRide_fare(historyList.getPrice())
                        .setRide_payment_type(historyList.getPayment())
                        .setRide_distance(historyList.getDistance())
                        .setRide_duration(historyList.getTripTime())
                        .setRide_captain_name(historyList.getCaptainName())
                        .setRide_captain_picture(historyList.getCaptainPicture())
                        .setRide_captain_ride_type(historyList.getRideTypeName())
                        .setRide_captain_car_brand(historyList.getBrandName())
                        .setRide_captain_car_name(historyList.getCarName())
                        .setRide_captain_car_colour(historyList.getCarColour())
                        .setRide_captain_number_plate(historyList.getCarNumberPlate())
                        .setRating(historyList.getRating()));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(rideHistoryJson.getCode())
                    .setMessage(rideHistoryJson.getMessage())
                    .setObjectArrayList(rideHistoryList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUser_id(userJson.getId())
                    .setUser_fName(userJson.getFName())
                    .setUser_lName(userJson.getLName())
                    .setUser_email(userJson.getEmail())
                    .setUser_password(userJson.getPass())
                    .setPhone(userJson.getPhone())
                    .setLogin_type(userJson.getUserType())
                    .setUser_picture(userJson.getAvatar());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUser_id(userJson.getId())
                    .setUser_fName(userJson.getFName())
                    .setUser_lName(userJson.getLName())
                    .setUser_email(userJson.getEmail())
                    .setUser_password(userJson.getPass())
                    .setPhone(userJson.getPhone())
                    .setLogin_type(userJson.getUserType())
                    .setUser_picture(userJson.getAvatar())
                    .setUserFavourite(new ArrayList<FavouriteList>(userJson.getFavouriteList()));

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

            GeneralResponse userJson = (GeneralResponse) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.CURRENT_RIDE) {

            RideHistoryJson rideHistoryJson = (RideHistoryJson) data;
            String order_id = null;

            if (rideHistoryJson.getHistoryList().size()>0){
                order_id = rideHistoryJson.getHistoryList().get(0).getId();
            }

            dataObject = new DataObject()
                    .setCode(rideHistoryJson.getCode())
                    .setMessage(rideHistoryJson.getMessage())
                    .setOrder_id(order_id);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {

            PrivacyPolicyJson privacyPolicyJson = (PrivacyPolicyJson) data;


            dataObject = new DataObject()
                    .setCode(privacyPolicyJson.getCode())
                    .setMessage(privacyPolicyJson.getMessage())
                    .setPrivacy_policy(privacyPolicyJson.getPrivacy());

        }


        return dataObject;

    }




    public DataObject() {
    }


    @Override
    public String toString() {
        return "DataObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_fName='" + user_fName + '\'' +
                ", user_lName='" + user_lName + '\'' +
                ", user_password='" + user_password + '\'' +
                ", login_type='" + login_type + '\'' +
                ", user_picture='" + user_picture + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_sign_in='" + user_sign_in + '\'' +
                ", user_remember='" + user_remember + '\'' +
                ", userFavourite=" + userFavourite +
                ", user_payment_type='" + user_payment_type + '\'' +
                ", ride_type_id='" + ride_type_id + '\'' +
                ", ride_type='" + ride_type + '\'' +
                ", ride_type_name='" + ride_type_name + '\'' +
                ", ride_type_tagline='" + ride_type_tagline + '\'' +
                ", ride_type_category='" + ride_type_category + '\'' +
                ", ride_type_image='" + ride_type_image + '\'' +
                ", selected_ride_type=" + selected_ride_type +
                ", location_label='" + location_label + '\'' +
                ", location_name='" + location_name + '\'' +
                ", location_Tagline='" + location_Tagline + '\'' +
                ", location_latitude=" + location_latitude +
                ", location_longitude=" + location_longitude +
                ", locationSelected=" + locationSelected +
                ", country_id='" + country_id + '\'' +
                ", country_name='" + country_name + '\'' +
                ", city_name='" + city_name + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city_latitude='" + city_latitude + '\'' +
                ", city_longitude='" + city_longitude + '\'' +
                ", payment_type_id='" + payment_type_id + '\'' +
                ", payment_type_name='" + payment_type_name + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", payment_type_picture='" + payment_type_picture + '\'' +
                ", selected_payment_type=" + selected_payment_type +
                ", selected_payment=" + selected_payment +
                ", currency_symbol='" + currency_symbol + '\'' +
                ", currency_name='" + currency_name + '\'' +
                ", fare_estimation='" + fare_estimation + '\'' +
                ", distance_estimation='" + distance_estimation + '\'' +
                ", duration_estimation='" + duration_estimation + '\'' +
                ", place_id='" + place_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", payment_card_no='" + payment_card_no + '\'' +
                ", payment_expiry_date='" + payment_expiry_date + '\'' +
                ", stripe_customer_no='" + stripe_customer_no + '\'' +
                ", payee_name='" + payee_name + '\'' +
                ", payment_card_company='" + payment_card_company + '\'' +
                ", captain_duration='" + captain_duration + '\'' +
                ", captain_id='" + captain_id + '\'' +
                ", captain_name='" + captain_name + '\'' +
                ", captain_phone='" + captain_phone + '\'' +
                ", captain_latitude='" + captain_latitude + '\'' +
                ", captain_longitude='" + captain_longitude + '\'' +
                ", captain_ride_type_id='" + captain_ride_type_id + '\'' +
                ", captain_ride_type_name='" + captain_ride_type_name + '\'' +
                ", captain_distance='" + captain_distance + '\'' +
                ", history_from_address='" + history_from_address + '\'' +
                ", history_from_latitude='" + history_from_latitude + '\'' +
                ", history_from_longitude='" + history_from_longitude + '\'' +
                ", history_to_address='" + history_to_address + '\'' +
                ", history_to_latitude='" + history_to_latitude + '\'' +
                ", history_to_longitude='" + history_to_longitude + '\'' +
                ", ride_date_time='" + ride_date_time + '\'' +
                ", ride_fare='" + ride_fare + '\'' +
                ", ride_payment_type='" + ride_payment_type + '\'' +
                ", ride_distance='" + ride_distance + '\'' +
                ", ride_duration='" + ride_duration + '\'' +
                ", ride_captain_name='" + ride_captain_name + '\'' +
                ", ride_captain_picture='" + ride_captain_picture + '\'' +
                ", ride_captain_ride_type='" + ride_captain_ride_type + '\'' +
                ", ride_captain_car_brand='" + ride_captain_car_brand + '\'' +
                ", ride_captain_car_name='" + ride_captain_car_name + '\'' +
                ", ride_captain_car_colour='" + ride_captain_car_colour + '\'' +
                ", ride_captain_number_plate='" + ride_captain_number_plate + '\'' +
                ", object_id='" + object_id + '\'' +
                ", object_min_delivery_time='" + object_min_delivery_time + '\'' +
                ", object_min_order_price='" + object_min_order_price + '\'' +
                ", object_delivery_charges='" + object_delivery_charges + '\'' +
                ", object_currency_symbol='" + object_currency_symbol + '\'' +
                ", object_latitude='" + object_latitude + '\'' +
                ", object_longitude='" + object_longitude + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", rating='" + rating + '\'' +
                ", reviewPictureList=" + reviewPictureList +
                ", coupon_id='" + coupon_id + '\'' +
                ", coupon_reward='" + coupon_reward + '\'' +
                ", post_id='" + post_id + '\'' +
                ", post_name='" + post_name + '\'' +
                ", post_price='" + post_price + '\'' +
                ", post_quantity='" + post_quantity + '\'' +
                ", post_attribute_id='" + post_attribute_id + '\'' +
                ", special_instructions='" + special_instructions + '\'' +
                ", order_id='" + order_id + '\'' +
                ", dataType=" + dataType +
                ", objectArrayList=" + objectArrayList +
                ", captainArraylist=" + captainArraylist +
                ", homeList=" + homeList +
                ", favourite_id='" + favourite_id + '\'' +
                ", cart_id='" + cart_id + '\'' +
                ", chatting='" + chatting + '\'' +
                ", picture='" + picture + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isFrom=" + isFrom +
                ", basePrice='" + basePrice + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.user_id);
        dest.writeString(this.user_fName);
        dest.writeString(this.user_lName);
        dest.writeString(this.user_password);
        dest.writeString(this.login_type);
        dest.writeString(this.user_picture);
        dest.writeString(this.user_phone);
        dest.writeString(this.user_email);
        dest.writeString(this.user_sign_in);
        dest.writeString(this.user_remember);
        dest.writeTypedList(this.userFavourite);
        dest.writeString(this.user_payment_type);
        dest.writeString(this.ride_type_id);
        dest.writeString(this.ride_type);
        dest.writeString(this.ride_type_name);
        dest.writeString(this.ride_type_tagline);
        dest.writeString(this.ride_type_category);
        dest.writeString(this.ride_type_image);
        dest.writeByte(this.selected_ride_type ? (byte) 1 : (byte) 0);
        dest.writeString(this.location_label);
        dest.writeString(this.location_name);
        dest.writeString(this.location_Tagline);
        dest.writeDouble(this.location_latitude);
        dest.writeDouble(this.location_longitude);
        dest.writeByte(this.locationSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.country_id);
        dest.writeString(this.country_name);
        dest.writeString(this.city_name);
        dest.writeString(this.city_id);
        dest.writeString(this.city_latitude);
        dest.writeString(this.city_longitude);
        dest.writeString(this.payment_type_id);
        dest.writeString(this.payment_type_name);
        dest.writeString(this.payment_type);
        dest.writeString(this.payment_type_picture);
        dest.writeByte(this.selected_payment_type ? (byte) 1 : (byte) 0);
        dest.writeByte(this.selected_payment ? (byte) 1 : (byte) 0);
        dest.writeString(this.currency_symbol);
        dest.writeString(this.currency_name);
        dest.writeString(this.fare_estimation);
        dest.writeString(this.distance_estimation);
        dest.writeString(this.duration_estimation);
        dest.writeString(this.place_id);
        dest.writeString(this.payment_id);
        dest.writeString(this.payment_card_no);
        dest.writeString(this.payment_expiry_date);
        dest.writeString(this.stripe_customer_no);
        dest.writeString(this.payee_name);
        dest.writeString(this.payment_card_company);
        dest.writeString(this.captain_duration);
        dest.writeString(this.captain_id);
        dest.writeString(this.captain_name);
        dest.writeString(this.captain_phone);
        dest.writeString(this.captain_latitude);
        dest.writeString(this.captain_longitude);
        dest.writeString(this.captain_ride_type_id);
        dest.writeString(this.captain_ride_type_name);
        dest.writeString(this.captain_distance);
        dest.writeString(this.history_from_address);
        dest.writeString(this.history_from_latitude);
        dest.writeString(this.history_from_longitude);
        dest.writeString(this.history_to_address);
        dest.writeString(this.history_to_latitude);
        dest.writeString(this.history_to_longitude);
        dest.writeString(this.ride_date_time);
        dest.writeString(this.ride_fare);
        dest.writeString(this.ride_payment_type);
        dest.writeString(this.ride_distance);
        dest.writeString(this.ride_duration);
        dest.writeString(this.ride_captain_name);
        dest.writeString(this.ride_captain_picture);
        dest.writeString(this.ride_captain_ride_type);
        dest.writeString(this.ride_captain_car_brand);
        dest.writeString(this.ride_captain_car_name);
        dest.writeString(this.ride_captain_car_colour);
        dest.writeString(this.ride_captain_number_plate);
        dest.writeString(this.object_id);
        dest.writeString(this.object_min_delivery_time);
        dest.writeString(this.object_min_order_price);
        dest.writeString(this.object_delivery_charges);
        dest.writeString(this.object_currency_symbol);
        dest.writeString(this.object_latitude);
        dest.writeString(this.object_longitude);
        dest.writeString(this.paymentType);
        dest.writeString(this.day);
        dest.writeString(this.fromTime);
        dest.writeString(this.toTime);
        dest.writeString(this.rating);
        dest.writeStringList(this.reviewPictureList);
        dest.writeString(this.coupon_id);
        dest.writeString(this.coupon_reward);
        dest.writeString(this.post_id);
        dest.writeString(this.post_name);
        dest.writeString(this.post_price);
        dest.writeString(this.post_quantity);
        dest.writeString(this.post_attribute_id);
        dest.writeString(this.special_instructions);
        dest.writeString(this.order_id);
        dest.writeInt(this.dataType == null ? -1 : this.dataType.ordinal());
        dest.writeTypedList(this.objectArrayList);
        dest.writeTypedList(this.captainArraylist);
        dest.writeList(this.homeList);
        dest.writeString(this.favourite_id);
        dest.writeString(this.cart_id);
        dest.writeString(this.chatting);
        dest.writeString(this.picture);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeByte(this.isFrom ? (byte) 1 : (byte) 0);
        dest.writeString(this.basePrice);
        dest.writeByte(this.fromSplash ? (byte) 1 : (byte) 0);
        dest.writeString(this.privacy_policy);
    }

    protected DataObject(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.user_id = in.readString();
        this.user_fName = in.readString();
        this.user_lName = in.readString();
        this.user_password = in.readString();
        this.login_type = in.readString();
        this.user_picture = in.readString();
        this.user_phone = in.readString();
        this.user_email = in.readString();
        this.user_sign_in = in.readString();
        this.user_remember = in.readString();
        this.userFavourite = in.createTypedArrayList(FavouriteList.CREATOR);
        this.user_payment_type = in.readString();
        this.ride_type_id = in.readString();
        this.ride_type = in.readString();
        this.ride_type_name = in.readString();
        this.ride_type_tagline = in.readString();
        this.ride_type_category = in.readString();
        this.ride_type_image = in.readString();
        this.selected_ride_type = in.readByte() != 0;
        this.location_label = in.readString();
        this.location_name = in.readString();
        this.location_Tagline = in.readString();
        this.location_latitude = in.readDouble();
        this.location_longitude = in.readDouble();
        this.locationSelected = in.readByte() != 0;
        this.country_id = in.readString();
        this.country_name = in.readString();
        this.city_name = in.readString();
        this.city_id = in.readString();
        this.city_latitude = in.readString();
        this.city_longitude = in.readString();
        this.payment_type_id = in.readString();
        this.payment_type_name = in.readString();
        this.payment_type = in.readString();
        this.payment_type_picture = in.readString();
        this.selected_payment_type = in.readByte() != 0;
        this.selected_payment = in.readByte() != 0;
        this.currency_symbol = in.readString();
        this.currency_name = in.readString();
        this.fare_estimation = in.readString();
        this.distance_estimation = in.readString();
        this.duration_estimation = in.readString();
        this.place_id = in.readString();
        this.payment_id = in.readString();
        this.payment_card_no = in.readString();
        this.payment_expiry_date = in.readString();
        this.stripe_customer_no = in.readString();
        this.payee_name = in.readString();
        this.payment_card_company = in.readString();
        this.captain_duration = in.readString();
        this.captain_id = in.readString();
        this.captain_name = in.readString();
        this.captain_phone = in.readString();
        this.captain_latitude = in.readString();
        this.captain_longitude = in.readString();
        this.captain_ride_type_id = in.readString();
        this.captain_ride_type_name = in.readString();
        this.captain_distance = in.readString();
        this.history_from_address = in.readString();
        this.history_from_latitude = in.readString();
        this.history_from_longitude = in.readString();
        this.history_to_address = in.readString();
        this.history_to_latitude = in.readString();
        this.history_to_longitude = in.readString();
        this.ride_date_time = in.readString();
        this.ride_fare = in.readString();
        this.ride_payment_type = in.readString();
        this.ride_distance = in.readString();
        this.ride_duration = in.readString();
        this.ride_captain_name = in.readString();
        this.ride_captain_picture = in.readString();
        this.ride_captain_ride_type = in.readString();
        this.ride_captain_car_brand = in.readString();
        this.ride_captain_car_name = in.readString();
        this.ride_captain_car_colour = in.readString();
        this.ride_captain_number_plate = in.readString();
        this.object_id = in.readString();
        this.object_min_delivery_time = in.readString();
        this.object_min_order_price = in.readString();
        this.object_delivery_charges = in.readString();
        this.object_currency_symbol = in.readString();
        this.object_latitude = in.readString();
        this.object_longitude = in.readString();
        this.paymentType = in.readString();
        this.day = in.readString();
        this.fromTime = in.readString();
        this.toTime = in.readString();
        this.rating = in.readString();
        this.reviewPictureList = in.createStringArrayList();
        this.coupon_id = in.readString();
        this.coupon_reward = in.readString();
        this.post_id = in.readString();
        this.post_name = in.readString();
        this.post_price = in.readString();
        this.post_quantity = in.readString();
        this.post_attribute_id = in.readString();
        this.special_instructions = in.readString();
        this.order_id = in.readString();
        int tmpDataType = in.readInt();
        this.dataType = tmpDataType == -1 ? null : Constant.DATA_TYPE.values()[tmpDataType];
        this.objectArrayList = in.createTypedArrayList(DataObject.CREATOR);
        this.captainArraylist = in.createTypedArrayList(DataObject.CREATOR);
        this.homeList = new ArrayList<Object>();
        in.readList(this.homeList, Object.class.getClassLoader());
        this.favourite_id = in.readString();
        this.cart_id = in.readString();
        this.chatting = in.readString();
        this.picture = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.isFrom = in.readByte() != 0;
        this.basePrice = in.readString();
        this.fromSplash = in.readByte() != 0;
        this.privacy_policy = in.readString();
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel source) {
            return new DataObject(source);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}
