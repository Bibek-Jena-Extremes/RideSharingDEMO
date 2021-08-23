package com.eztruck.eztruckcustomer.FragmentUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.eztruck.eztruckcustomer.ActivityUtil.AddBilling;
import com.eztruck.eztruckcustomer.ActivityUtil.Base;
import com.eztruck.eztruckcustomer.ActivityUtil.FromLocationSelector;
import com.eztruck.eztruckcustomer.ActivityUtil.TrackRider;
import com.eztruck.eztruckcustomer.AdapterUtil.RideTypeAdapter;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.DateTimeObject;
import com.eztruck.eztruckcustomer.ObjectUtil.GeocodeObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;


public class HomeFragment extends Fragment implements View.OnClickListener, ConnectionCallback,
        OnLocationUpdatedListener, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    private String TAG = HomeFragment.class.getName();
    private Management management;
    private PrefObject prefObject;

    /* Variable for Ride , Payment
     * list of Captain list */

    private ArrayList<Marker> markerList = new ArrayList<>();
    final ArrayList<Object> rideArraylist = new ArrayList<>();
    final ArrayList<Object> paymentArraylist = new ArrayList<>();
    final ArrayList<DataObject> captainArraylist = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private HashMap<String, String> favouriteMap = new HashMap<>();

    /* Variable for Location Listener
     *  and storing current location */

    private SmartLocation smartLocation;
    private LatLng locationObject;
    private int PLACE_SELECTION_REQUEST_CODE = 12;

    /* Variable for Google Map View*/

    private MapView mapView;
    private GoogleMap googleMap;

    /* Variable for Ride & Payment Type */

    private CardView cardRideType;
    private CardView cardSchedule;

    /* Variable for Pick Up , DropOff
     *  Start Ride Views */

    private LinearLayout layoutPickUp;
    private CardView cardPickUp;
    private TextView txtRideTypeName;
    private ImageView imageRideType;
    private String rideType;
    private String rideTypeId;

    /////////

    private LinearLayout layoutDropOff;
    private CardView cardDropOff;
    private CardView cardSkip;

    //////

    private LinearLayout layoutDone;
    private CardView cardStart;
    private LinearLayout layoutRide;
    private TextView txtRideName;
    private ImageView imageRide;
    private TextView txtPromo;
    private LinearLayout layoutPromo;

    private LinearLayout layoutPaymentType;
    private TextView txtPaymentType;
    private ImageView imagePayment;
    private String paymentType;
    private String paymentTypeId;

    /* Variable for Menu , Back
     *  Source Green Line */

    private TextView lineFrom;
    private ImageView imageBack;
    private TextView txtMenu;

    /* Variable for Source , Destination
     *  Addresses Cards */

    private RelativeLayout layoutFromAddress;
    private TextView txtFrom;
    private TextView txtFromAddress;

    private RelativeLayout layoutToAddress;
    private TextView txtTo;
    private TextView txtToAddress;

    /* Variable for Source , Destination
     *  Location Coordinates */

    private double fromLatitude = 0.0;
    private double fromLongitude;
    private double toLatitude;
    private double toLongitude;
    private double captainLatitude;
    private double captainLongitude;

    /* Variable for Estimated Time Marker */

    private LinearLayout layoutError;
    private LinearLayout layoutMarker;
    private LinearLayout layoutNoCar;
    private TextView txtError;
    private TextView txtMarker;
    private TextView txtEstimationTime;
    private String distanceCalculated;
    private String durationCalculation;
    private String paymentCustomerNo;
    private TextView txtFairPrice;

    /* Variable for Estimated Time Card*/

    private LinearLayout layoutEst;
    private TextView txtEst;
    private LinearLayout layoutDestination;

    /* Variable for Map Type Selection
        & Current Location*/

    private CardView cardMapType;
    private ImageView imageMapType;
    private CardView cardCurrentLocation;


    /* Variable for Payment Type*/

    private ProgressBar progressRideType;
    private ProgressBar progressFair;

    private String currency_name;
    private String currency_symbol;
    private String placeId;
    private String city;
    private boolean isSelectedFromPicker = false;
    private boolean isSelectedToPicker = false;
    private String selectedDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUI(view, savedInstanceState); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view, Bundle savedInstanceState) {

        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.select_pick_up_location));

        imageBack = view.findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_menu);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setTag(0);

        progressRideType = view.findViewById(R.id.progress_ride_type);
        progressFair = view.findViewById(R.id.progress_fair);

        Utility.Logger(TAG, prefObject.toString());

        startLocation();

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();   // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardMapType = view.findViewById(R.id.card_map_type);
        cardMapType.setTag(0);
        cardCurrentLocation = view.findViewById(R.id.card_current_location);
        imageMapType = view.findViewById(R.id.image_map_type);

        layoutMarker = view.findViewById(R.id.layout_marker);
        txtMarker = view.findViewById(R.id.txt_marker);
        layoutEst = view.findViewById(R.id.layout_est);
        txtEst = view.findViewById(R.id.txt_est);
        layoutDestination = view.findViewById(R.id.layout_destination);
        layoutError = view.findViewById(R.id.layout_error);
        txtError = view.findViewById(R.id.txt_error);
        layoutNoCar = view.findViewById(R.id.layout_no_car);


        ///Address Selection

        layoutToAddress = view.findViewById(R.id.layout_to_address);
        layoutFromAddress = view.findViewById(R.id.layout_from_address);
        txtFrom = view.findViewById(R.id.txt_from);
        txtFromAddress = view.findViewById(R.id.txt_from_address);
        txtTo = view.findViewById(R.id.txt_to);
        txtToAddress = view.findViewById(R.id.txt_to_address);
        lineFrom = view.findViewById(R.id.line_from);


        ///Pick Up functionality

        layoutPickUp = view.findViewById(R.id.layout_pick_up);
        cardPickUp = view.findViewById(R.id.card_pick_up);
        cardRideType = view.findViewById(R.id.card_ride_type);
        cardSchedule = view.findViewById(R.id.card_schedule);
        txtRideTypeName = view.findViewById(R.id.txt_ride_type_name);
        txtEstimationTime = view.findViewById(R.id.txt_estimation_time);
        imageRideType = view.findViewById(R.id.image_ride_type);

        ///Drop off functionality

        layoutDropOff = view.findViewById(R.id.layout_drop_off);
        cardDropOff = view.findViewById(R.id.card_drop_off);
        cardSkip = view.findViewById(R.id.card_skip);

        ///Done functionality

        layoutDone = view.findViewById(R.id.layout_done);
        cardStart = view.findViewById(R.id.card_start);
        layoutPaymentType = view.findViewById(R.id.layout_payment_type);
        layoutRide = view.findViewById(R.id.layout_ride);
        txtRideName = view.findViewById(R.id.txt_ride_name);
        imageRide = view.findViewById(R.id.image_ride);
        txtFairPrice = view.findViewById(R.id.txt_ride_price);
        imagePayment = view.findViewById(R.id.image_payment);
        txtPaymentType = view.findViewById(R.id.txt_payment_type);
        paymentType = "cash";
        layoutPromo = view.findViewById(R.id.layout_promo);
        txtPromo = view.findViewById(R.id.txt_promo);


        imageBack.setOnClickListener(this);
        layoutPaymentType.setOnClickListener(this);
        layoutRide.setOnClickListener(this);
        cardRideType.setOnClickListener(this);
        cardSchedule.setOnClickListener(this);
        cardMapType.setOnClickListener(this);
        cardCurrentLocation.setOnClickListener(this);
        cardPickUp.setOnClickListener(this);
        cardDropOff.setOnClickListener(this);
        cardSkip.setOnClickListener(this);
        cardStart.setOnClickListener(this);
        layoutFromAddress.setOnClickListener(this);
        layoutToAddress.setOnClickListener(this);
        layoutPromo.setOnClickListener(this);
        mapView.getMapAsync(this);

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getJson(String latitude, String longitude, String city, String user_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "retrieve_ride_type");
            jsonObject.accumulate("latitude", latitude);
            jsonObject.accumulate("longitude", longitude);
            jsonObject.accumulate("city", city);
            jsonObject.accumulate("user_id", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getEstimatedTimeJson(String latitude, String longitude, String ride_type_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "find_nearest_ride_distance");
            jsonObject.accumulate("latitude", latitude);
            jsonObject.accumulate("longitude", longitude);
            jsonObject.accumulate("ride_type_id", ride_type_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getEstimatedFarePriceJson(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, String ride_type_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "find_estimated_fare_price");
            jsonObject.accumulate("latitude", fromLatitude);
            jsonObject.accumulate("longitude", fromLongitude);
            jsonObject.accumulate("to_latitude", toLatitude);
            jsonObject.accumulate("to_longitude", toLongitude);
            jsonObject.accumulate("ride_type_id", ride_type_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getCouponJson(String coupon_code, String place_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "verify_coupon");
            jsonObject.accumulate("coupon_code", coupon_code);
            jsonObject.accumulate("place_id", place_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getOrderJson(String functionality, String ride_type_id, String user_id, String from_address, String from_latitude, String from_longitude,
                                String to_address, String to_latitude, String to_longitude, String distance,
                                String price, String time, String payment, String payment_customer_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("ride_type_id", ride_type_id);
            jsonObject.accumulate("user_id", user_id);
            jsonObject.accumulate("from_address", from_address);
            jsonObject.accumulate("latitude", from_latitude);
            jsonObject.accumulate("longitude", from_longitude);
            jsonObject.accumulate("to_address", to_address);
            jsonObject.accumulate("to_latitude", to_latitude);
            jsonObject.accumulate("to_longitude", to_longitude);
            jsonObject.accumulate("distance", distance);
            jsonObject.accumulate("price", price);
            jsonObject.accumulate("time", time);
            jsonObject.accumulate("payment", payment);
            jsonObject.accumulate("currency_symbol", currency_symbol);
            jsonObject.accumulate("customer_payment_id", payment_customer_id);
            jsonObject.accumulate("schedule_date", selectedDate);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to start location & get userObject current location</p>
     */
    private void startLocation() {

        smartLocation = new SmartLocation.Builder(getActivity()).logging(true).build();
        smartLocation.location().config(LocationParams.BEST_EFFORT).oneFix().start(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            int tag = (int) imageBack.getTag();
            if (tag == 0) {
                Base.layoutDrawer.openDrawer(Gravity.LEFT);
            } else if (tag == 1) {

                cardSkip.callOnClick();

            } else if (tag == 2) {

                layoutDropOff.setVisibility(View.VISIBLE);

                txtMarker.setVisibility(View.GONE);
                layoutDestination.setVisibility(View.VISIBLE);
                layoutMarker.setVisibility(View.VISIBLE);

                layoutDone.setVisibility(View.GONE);
                txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.select_drop_off_location));
                imageBack.setTag(1);

                Utility.Logger(TAG, "Drop Off = " + markerList.size());

                if (markerList.size() > 1) {
                    markerList.get(1).remove();
                    markerList.remove(1);
                }

                progressFair.setVisibility(View.GONE);
                txtFairPrice.setVisibility(View.GONE);

            }
        }
        if (v == cardRideType) {

            if (rideArraylist.size() <= 0) {

                Utility.Toaster(getActivity(),
                        Utility.getStringFromRes(getActivity(), R.string.no_ride_available)
                        , Toast.LENGTH_SHORT);

                return;
            }

            showRideTypeBottomSheet(getActivity(), rideType, false);
        }
        if (v == cardSchedule) {
            showScheduleBottomSheet(getActivity());
        }
        if (v == cardPickUp) {

            if (rideArraylist.size() <= 0) {
                return;
            }

            layoutPickUp.setVisibility(View.GONE);
            layoutDropOff.setVisibility(View.VISIBLE);

            lineFrom.setVisibility(View.VISIBLE);
            layoutToAddress.setVisibility(View.VISIBLE);

            imageBack.setImageResource(R.drawable.ic_back_arrow);
            imageBack.setTag(1);

            layoutEst.setVisibility(View.GONE);
            txtMarker.setVisibility(View.GONE);

            txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.select_drop_off_location));


            // Placing a marker on the Selected Source Position

            MarkerOptions markerOptions = null;

            if (layoutError.getVisibility() == View.VISIBLE) {

                layoutError.setVisibility(View.GONE);
                View view = getLayoutInflater().inflate(R.layout.no_car_marker_layout, null);
                markerOptions = Utility.createMarkerForMap(null
                        , new LatLng(fromLatitude, fromLongitude)
                        , Utility.getHdBitmapFromView(view));

            } else {

                markerOptions = Utility.createMarkerForMap(null
                        , new LatLng(fromLatitude, fromLongitude)
                        , Utility.getBitmapFromView(layoutEst));

            }

            Marker fromMarker = googleMap.addMarker(markerOptions);
            fromMarker.setTag(0);

            markerList.add(fromMarker);
            layoutDestination.setVisibility(View.VISIBLE);

        }
        if (v == cardDropOff) {

            if (toLatitude == 0.0 || toLongitude == 0.0) {

                Utility.Toaster(getActivity(),
                        Utility.getStringFromRes(getActivity(), R.string.select_drop_off_location_message),
                        Toast.LENGTH_SHORT);

                return;
            }

            layoutDropOff.setVisibility(View.GONE);
            layoutDone.setVisibility(View.VISIBLE);
            layoutDestination.setVisibility(View.GONE);
            txtMarker.setVisibility(View.GONE);
            layoutMarker.setVisibility(View.GONE);


            imageBack.setTag(2);
            txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.let_start_ride));

            // Placing a marker on the Selected Source Position

            MarkerOptions markerOptions = Utility.createMarkerForMap(null
                    , new LatLng(toLatitude, toLongitude)
                    , Utility.getBitmapFromView(layoutDestination));

            Marker toMarker = googleMap.addMarker(markerOptions);
            toMarker.setTag(1);

            markerList.add(toMarker);
            findEstimatedFare();


        }
        if (v == cardSkip) {

            layoutDropOff.setVisibility(View.GONE);
            layoutPickUp.setVisibility(View.VISIBLE);
            layoutToAddress.setVisibility(View.GONE);
            lineFrom.setVisibility(View.GONE);

            layoutMarker.setVisibility(View.VISIBLE);
            txtMarker.setVisibility(View.VISIBLE);
            layoutDestination.setVisibility(View.GONE);

            imageBack.setImageResource(R.drawable.ic_menu);
            imageBack.setTag(0);

            Utility.Logger(TAG, "Pick Up = " + markerList.size());

            if (markerList.size() > 0) {
                markerList.get(0).remove();
                markerList.remove(0);
            }


            txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.select_pick_up_location));
            txtTo.setText(Utility.getStringFromRes(getActivity(), R.string.where_do_you_want_to_go));
            txtTo.setTextColor(Utility.getAttrColor(getActivity(), R.attr.colorTagline));
            txtToAddress.setVisibility(View.GONE);


        }
        if (v == cardStart) {

            String order_type;

            if (!Utility.isEmptyString(selectedDate)) {
                order_type = "schedule_ride";
            } else {
                order_type = "book_ride";
            }


            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setLoadingText(Utility.getStringFromRes(getActivity(), R.string.book_ride))
                    .setJson(getOrderJson(order_type, rideTypeId,/*prefObject.getUserId()*/"1", txtFromAddress.getText().toString(), String.valueOf(fromLatitude), String.valueOf(fromLongitude)
                            , txtToAddress.getText().toString(), String.valueOf(toLatitude), String.valueOf(toLongitude), distanceCalculated, txtFairPrice.getText().toString()
                            , durationCalculation, paymentTypeId, paymentCustomerNo))  //TODO User ID
                    .setConnection(Constant.CONNECTION.BOOK_RIDE)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnectionCallback(this));

        }
        if (v == layoutFromAddress) {

            if (layoutPickUp.getVisibility() == View.VISIBLE) {
                Intent intent = new Intent(getActivity(), FromLocationSelector.class);
                intent.putExtra(Constant.IntentKey.SOURCE_CITY, city);
                intent.putExtra(Constant.IntentKey.SOURCE_LATITUDE, locationObject.latitude);
                intent.putExtra(Constant.IntentKey.SOURCE_LONGITUDE, locationObject.longitude);
                startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_FROM);
            }

        }
        if (v == layoutToAddress) {

            if (layoutDropOff.getVisibility() == View.VISIBLE) {
                Intent intent = new Intent(getActivity(), FromLocationSelector.class);
                intent.putExtra(Constant.IntentKey.SOURCE_CITY, city);
                intent.putExtra(Constant.IntentKey.LOCATION_SELECTOR, true);
                startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_TO);
            }

        }
        if (v == cardCurrentLocation) {
            setUpMarker(locationObject.latitude, locationObject.longitude);
        }
        if (v == cardMapType) {

            int tag = (int) cardMapType.getTag();
            if (tag == 0) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                cardMapType.setTag(1);
                imageMapType.setColorFilter(Utility.getAttrColor(getActivity(), R.attr.colorGreen));
            } else {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                cardMapType.setTag(0);
                imageMapType.setColorFilter(Utility.getAttrColor(getActivity(), R.attr.colorTagline));
            }
        }
        if (v == layoutPaymentType) {
            showPaymentTypeBottomSheet(getActivity(), paymentType);
        }
        if (v == layoutRide) {

            if (rideArraylist.size() <= 0) {

                Utility.Toaster(getActivity(),
                        Utility.getStringFromRes(getActivity(), R.string.no_ride_available)
                        , Toast.LENGTH_SHORT);

                return;
            }

            showRideTypeBottomSheet(getActivity(), rideType, true);
        }
        if (v == layoutPromo) {
            showCouponBottomSheet(getActivity());
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {
            if (data instanceof DataObject) {

                DataObject dataObject = (DataObject) data;

                if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_RIDE_PAYMENT_TYPE) {

                    rideArraylist.clear();
                    paymentArraylist.clear();
                    captainArraylist.clear();

                    if (!cardPickUp.isEnabled())
                        cardPickUp.setEnabled(true);

                    if (imageRide.getVisibility() == View.GONE) {
                        imageRide.setVisibility(View.VISIBLE);
                    }

                    if (txtRideName.getVisibility() == View.GONE) {
                        txtRideName.setVisibility(View.VISIBLE);
                    }

                    currency_symbol = dataObject.getCurrency_symbol();
                    currency_name = dataObject.getCurrency_name();
                    placeId = dataObject.getPlace_id();

                    rideArraylist.addAll(dataObject.getObjectArrayList());
                    paymentArraylist.addAll(dataObject.getHomeList());
                    captainArraylist.addAll(dataObject.getCaptainArraylist());
                    paymentArraylist.add(new DataObject().setDataType(Constant.DATA_TYPE.ADD_CARD));

                    for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                        DataObject rideObject = dataObject.getObjectArrayList().get(i);
                        if (rideObject.getRide_type_category().equalsIgnoreCase("Standard")) {
                            txtRideTypeName.setText(rideObject.getRide_type_name());
                            txtRideName.setText(rideObject.getRide_type_name());
                            rideType = rideObject.getRide_type();
                            rideTypeId = rideObject.getRide_type_id();

                            Glide.with(getContext()).load(Constant.ServerInformation.PICTURE_URL + rideObject.getRide_type_image())
                                    .into(imageRideType);

                            Glide.with(getContext()).load(Constant.ServerInformation.PICTURE_URL + rideObject.getRide_type_image())
                                    .into(imageRide);

                            break;
                        }
                    }

                    for (int i = 0; i < dataObject.getHomeList().size(); i++) {

                        DataObject paymentObject = (DataObject) dataObject.getHomeList().get(i);
                        if (paymentObject.getPayment_type().equalsIgnoreCase("cash")) {
                            txtPaymentType.setText(paymentObject.getPayment_type_name());
                            paymentType = paymentObject.getPayment_type();
                            paymentTypeId = paymentObject.getPayment_type_id();

                            Glide.with(getContext()).load(Constant.ServerInformation.PICTURE_URL + paymentObject.getPayment_type_picture())
                                    .into(imagePayment);

                            break;
                        }
                    }

                    ///find nearest Captain & Ride Type

                    findNearestCaptainAndSpecificRideType();


                } else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_PICK_UP_TIME) {

                    Utility.Logger(TAG, "Estimated PickUp Time");

                    for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                        DataObject captainObject = dataObject.getObjectArrayList().get(i);
                        txtEst.setText(captainObject.getCaptain_duration());
                        txtEst.setVisibility(View.VISIBLE);
                        layoutEst.setVisibility(View.VISIBLE);
                        layoutMarker.setVisibility(View.VISIBLE);
                        txtMarker.setVisibility(View.GONE);
                        progressRideType.setVisibility(View.GONE);
                        txtEstimationTime.setText(captainObject.getCaptain_duration());
                        txtEstimationTime.setVisibility(View.VISIBLE);

                        Utility.Logger(TAG, "Estimated Duration = " + captainObject.getCaptain_duration());

                    }

                } else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_FARE_PRICE) {

                    progressFair.setVisibility(View.GONE);
                    txtFairPrice.setText(currency_symbol + " " + dataObject.getFare_estimation());
                    distanceCalculated = dataObject.getDistance_estimation();
                    durationCalculation = dataObject.getDuration_estimation();
                    txtFairPrice.setVisibility(View.VISIBLE);

                } else if (requestObject.getConnection() == Constant.CONNECTION.BOOK_RIDE) {

                    if (!Utility.isEmptyString(selectedDate)) {

                        Utility.Toaster(getActivity(),
                                Utility.getStringFromRes(getActivity(), R.string.ride_scheduled_successfully),
                                Toast.LENGTH_LONG);

                        getActivity().finish();

                        return;
                    }

                    Intent intent = new Intent(getActivity(), TrackRider.class);
                    intent.putExtra(Constant.IntentKey.ORDER_DETAIL, dataObject);
                    startActivity(intent);
                    getActivity().finish();

                }


            }

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

        if (!Utility.isEmptyString(data) && requestObject != null) {
            Utility.Logger(TAG, "Error = " + data);

            if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_RIDE_PAYMENT_TYPE) {

                layoutError.setVisibility(View.VISIBLE);
                txtError.setText(Utility.getStringFromRes(getActivity(), R.string.we_did_not_operate_area));
                layoutEst.setVisibility(View.GONE);
                progressRideType.setVisibility(View.GONE);
                progressFair.setVisibility(View.GONE);
                txtFairPrice.setVisibility(View.GONE);
                imageRide.setVisibility(View.GONE);
                txtRideName.setVisibility(View.GONE);
                cardPickUp.setEnabled(false);

            } else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_PICK_UP_TIME) {

                layoutError.setVisibility(View.VISIBLE);
                txtError.setText(Utility.getStringFromRes(getActivity(), R.string.no_ride_available));
                txtMarker.setVisibility(View.GONE);
                layoutEst.setVisibility(View.GONE);
                progressRideType.setVisibility(View.GONE);
                progressFair.setVisibility(View.GONE);
                txtFairPrice.setVisibility(View.GONE);


            } else if (requestObject.getConnection() == Constant.CONNECTION.BOOK_RIDE) {

                Utility.Toaster(getActivity(), data, Toast.LENGTH_LONG);
                layoutMarker.setVisibility(View.GONE);
                layoutEst.setVisibility(View.GONE);
                progressRideType.setVisibility(View.GONE);
                progressFair.setVisibility(View.GONE);
                txtFairPrice.setVisibility(View.GONE);


            } else {

                layoutMarker.setVisibility(View.GONE);
                layoutEst.setVisibility(View.GONE);
                progressRideType.setVisibility(View.GONE);
                progressFair.setVisibility(View.GONE);
                txtFairPrice.setVisibility(View.GONE);

            }


        }

    }


    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_CODE_FROM && resultCode == getActivity().RESULT_OK) {

            DataObject locationSelector = data.getParcelableExtra(Constant.IntentKey.LOCATION_SELECTOR);
            txtFrom.setText(locationSelector.getLocation_name());
            txtFromAddress.setText(locationSelector.getLocation_Tagline());
            fromLatitude = locationSelector.getLocation_latitude();
            fromLongitude = locationSelector.getLocation_longitude();

            isSelectedFromPicker = true;
            setUpMarker(fromLatitude, fromLongitude);

        } else if (requestCode == Constant.RequestCode.REQUEST_CODE_TO && resultCode == getActivity().RESULT_OK) {

            DataObject locationSelector = data.getParcelableExtra(Constant.IntentKey.LOCATION_SELECTOR);
            txtTo.setText(locationSelector.getLocation_name());
            txtToAddress.setText(locationSelector.getLocation_Tagline());
            toLatitude = locationSelector.getLocation_latitude();
            toLongitude = locationSelector.getLocation_longitude();

            txtTo.setTextColor(Utility.getAttrColor(getActivity(), R.attr.colorNormal));
            txtToAddress.setVisibility(View.VISIBLE);

            isSelectedToPicker = true;
            setUpMarker(toLatitude, toLongitude);


        } else if (requestCode == Constant.RequestCode.REQUEST_CODE_ADD_CARD && resultCode == getActivity().RESULT_OK) {

            DataObject addCard = data.getParcelableExtra(Constant.IntentKey.CARD_INFORMATION);
            Utility.Logger(TAG, "Payment " + addCard.getPayment_card_no() + " " + addCard.getPayment_expiry_date() + " " + addCard.getPayment_id());

            paymentArraylist.remove((paymentArraylist.size() - 1));
            paymentArraylist.add(addCard);
            paymentArraylist.add(new DataObject().setDataType(Constant.DATA_TYPE.ADD_CARD));

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        /*if (!prefObject.isLogin())
            return;

        favouriteArraylist.clear();
        favouriteArraylist.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.FAVOURITES)
                .setDbOperation(Constant.DB.SPECIFIC_ID)
                .setDataObject(new DataObject()
                        .setUser_id(prefObject.getUserId()))));

        favouriteMap.clear();

        for (int i = 0; i < favouriteArraylist.size(); i++) {
            DataObject favouriteObject = (DataObject) favouriteArraylist.get(i);
            favouriteMap.put(favouriteObject.getObject_id(), favouriteObject.getUser_id());
        }*/


    }


    @Override
    public void onLocationUpdated(Location location) {
        if (location != null) {

            Utility.Logger(TAG, "Location = Latitude (" + location.getLatitude() + "," + location.getLongitude() + ")");

            GeocodeObject geoCodeObject = Utility.getGeoCodeObject(getActivity(), location.getLatitude(), location.getLongitude());
            ///GeocodeObject geoCodeObject = Utility.getGeoCodeObject(getActivity(), Double.parseDouble("31.4123541"), Double.parseDouble("73.1136876"));
            txtFrom.setText(geoCodeObject.getCity());
            ///city = geoCodeObject.getCity();
            txtFromAddress.setText(geoCodeObject.getAddress());

            Constant.setBaseLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            locationObject = new LatLng(location.getLatitude(), location.getLongitude());
            fromLatitude = location.getLatitude();
            fromLongitude = location.getLongitude();

            setUpMarker(fromLatitude, fromLongitude);


        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setZoomGesturesEnabled(false);
        this.googleMap.setOnCameraIdleListener(this);


        this.googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

                if (i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {

                    Utility.Logger(TAG, "The user gestured on the map");
                    layoutMarker.setVisibility(View.VISIBLE);
                    txtMarker.setVisibility(View.VISIBLE);
                    layoutError.setVisibility(View.GONE);
                    layoutEst.setVisibility(View.GONE);
                    layoutDestination.setVisibility(View.GONE);

                }
                else if (i == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION) {

                    Utility.Logger(TAG, "The user tapped something on the map.");

                }
                else if (i == GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {

                    Utility.Logger(TAG, "The app moved the camera.");
                    layoutMarker.setVisibility(View.VISIBLE);
                    txtMarker.setVisibility(View.VISIBLE);
                    layoutEst.setVisibility(View.GONE);
                    layoutDestination.setVisibility(View.GONE);
                    layoutError.setVisibility(View.GONE);

                }

            }
        });


        setUpMarker(fromLatitude, fromLongitude);

    }


    @Override
    public void onCameraIdle() {

        LatLng targetCoordinates = googleMap.getCameraPosition().target;
        GeocodeObject geocodeObject = Utility.getGeoCodeObject(getActivity(), targetCoordinates.latitude, targetCoordinates.longitude);
        boolean isWithinSameCity = false;

        if (geocodeObject == null)
            return;

        if (layoutPickUp.getVisibility() == View.VISIBLE) {

            Utility.Logger(TAG, "Pick Up");

            if (!isSelectedFromPicker) {

                fromLatitude = targetCoordinates.latitude;
                fromLongitude = targetCoordinates.longitude;

                txtFrom.setText(geocodeObject.getKnownName());
                txtFromAddress.setText(geocodeObject.getAddress());


            }
            isSelectedFromPicker = false;

            if (geocodeObject.getCity().equalsIgnoreCase(city) && !Utility.isEmptyString(city)) {

                ///find nearest Captain & Ride Type

                findNearestCaptainAndSpecificRideType();
                isWithinSameCity = true;

            }else {
                isWithinSameCity = false;
            }

            city = geocodeObject.getCity();
            progressRideType.setVisibility(View.VISIBLE);
            txtEstimationTime.setVisibility(View.GONE);

            if (layoutEst.getVisibility() == View.VISIBLE) {
                layoutEst.setVisibility(View.GONE);
            }

            if (isWithinSameCity)
                return;

            //Send request to Server

            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setJson(getJson(String.valueOf(fromLatitude), String.valueOf(fromLongitude), geocodeObject.getCity(),/*prefObject.getUserId()*/"1"))  //TODO User ID
                    .setConnection(Constant.CONNECTION.RETRIEVE_RIDE_PAYMENT_TYPE)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnectionCallback(this));

        }
        else if (layoutDropOff.getVisibility() == View.VISIBLE) {

            Utility.Logger(TAG, "Drop Off");

            if (!isSelectedToPicker) {

                toLatitude = targetCoordinates.latitude;
                toLongitude = targetCoordinates.longitude;

                txtTo.setText(geocodeObject.getKnownName());
                txtToAddress.setText(geocodeObject.getAddress());

            }

            isSelectedToPicker = false;
            txtTo.setTextColor(Utility.getAttrColor(getActivity(), R.attr.colorNormal));
            txtToAddress.setVisibility(View.VISIBLE);

            layoutMarker.setVisibility(View.VISIBLE);
            txtMarker.setVisibility(View.GONE);
            layoutDestination.setVisibility(View.VISIBLE);

        }


    }


    /**
     * <p>Find nearest captain along with Specific Ride Type</p>
     */
    private void findNearestCaptainAndSpecificRideType() {

        progressRideType.setVisibility(View.VISIBLE);
        txtEstimationTime.setVisibility(View.GONE);

        if (layoutEst.getVisibility() == View.VISIBLE) {
            layoutEst.setVisibility(View.GONE);
        }

        management.sendRequestToServer(new RequestObject()
                .setContext(getActivity())
                .setJson(getEstimatedTimeJson(String.valueOf(fromLatitude), String.valueOf(fromLongitude), rideTypeId))
                .setConnection(Constant.CONNECTION.ESTIMATED_PICK_UP_TIME)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));


    }


    /**
     * <p>Find nearest captain along with Specific Ride Type</p>
     */
    private void findEstimatedFare() {

        progressFair.setVisibility(View.VISIBLE);
        txtFairPrice.setVisibility(View.GONE);
        ///txtEstimationTime.setVisibility(View.GONE);


        management.sendRequestToServer(new RequestObject()
                .setContext(getActivity())
                .setJson(getEstimatedFarePriceJson(String.valueOf(fromLatitude), String.valueOf(fromLongitude),
                        String.valueOf(toLatitude), String.valueOf(toLongitude), rideTypeId))
                .setConnection(Constant.CONNECTION.ESTIMATED_FARE_PRICE)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));


    }


    /**
     * <p>It is used to show Ride Type Selection</p>
     *
     * @param context
     */
    private void showRideTypeBottomSheet(final Context context, final String rider_type, final boolean fareEstimation) {
        final View view = getLayoutInflater().inflate(R.layout.ride_type_bottom_sheet, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        for (int i = 0; i < rideArraylist.size(); i++) {

            DataObject rideObject = (DataObject) rideArraylist.get(i);
            if (rideObject.getRide_type().equalsIgnoreCase(rider_type))
                rideObject.setSelected_ride_type(true);
            else
                rideObject.setSelected_ride_type(false);

        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewRideType = view.findViewById(R.id.recycler_view_ride_type);
        recyclerViewRideType.setLayoutManager(gridLayoutManager);

        RideTypeAdapter rideTypeAdapter = new RideTypeAdapter(getActivity(), rideArraylist) {
            @Override
            public void onRideTypeSelectionListener(int position) {

                DataObject riderTypeObject = (DataObject) rideArraylist.get(position);
                rideType = riderTypeObject.getRide_type();
                txtRideTypeName.setText(riderTypeObject.getRide_type_name());
                txtRideName.setText(riderTypeObject.getRide_type_name());
                rideTypeId = riderTypeObject.getRide_type_id();


                Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + riderTypeObject.getRide_type_image())
                        .into(imageRideType);

                Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + riderTypeObject.getRide_type_image())
                        .into(imageRide);

                if (fareEstimation) {
                    findEstimatedFare();
                } else {
                    findNearestCaptainAndSpecificRideType();
                }

                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }


            }
        };
        recyclerViewRideType.setAdapter(rideTypeAdapter);


    }


    /**
     * <p>It is used to show Payment Selection</p>
     *
     * @param context
     */
    private void showPaymentTypeBottomSheet(final Context context, final String payment_type) {
        final View view = getLayoutInflater().inflate(R.layout.ride_type_bottom_sheet, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        for (int i = 0; i < paymentArraylist.size(); i++) {

            DataObject rideObject = (DataObject) paymentArraylist.get(i);

            if (rideObject.getDataType() == Constant.DATA_TYPE.ADD_CARD) {
                continue;
            }

            if (rideObject.getPayment_type().equalsIgnoreCase(payment_type))
                rideObject.setSelected_payment(true);
            else
                rideObject.setSelected_payment(false);

        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewRideType = view.findViewById(R.id.recycler_view_ride_type);
        recyclerViewRideType.setLayoutManager(gridLayoutManager);

        RideTypeAdapter rideTypeAdapter = new RideTypeAdapter(getActivity(), paymentArraylist) {
            @Override
            public void onRideTypeSelectionListener(int position) {

                DataObject riderTypeObject = (DataObject) paymentArraylist.get(position);

                if (riderTypeObject.getDataType() == Constant.DATA_TYPE.ADD_CARD) {
                    Utility.Logger(TAG, "Adding Card Functionality");

                    Intent intent = new Intent(context, AddBilling.class);
                    startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_ADD_CARD);

                    if (bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }

                    return;
                }

                paymentType = riderTypeObject.getPayment_type();

                Utility.Logger(TAG, "Payment Type = " + paymentType + " Payment " + riderTypeObject.getPayment_type());

                if (riderTypeObject.getDataType() == Constant.DATA_TYPE.CARD_TYPE) {

                    txtPaymentType.setText(riderTypeObject.getPayment_type_name());

                } else if (riderTypeObject.getDataType() == Constant.DATA_TYPE.CARD_DETAIL) {

                    txtPaymentType.setText(Utility.maskSomeCharacter(riderTypeObject.getPayment_card_no()));
                    paymentCustomerNo = riderTypeObject.getStripe_customer_no();

                }

                Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + riderTypeObject.getPayment_type_picture())
                        .into(imagePayment);

                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }


            }
        };
        recyclerViewRideType.setAdapter(rideTypeAdapter);


    }


    /**
     * <p>It is used to show Payment Selection</p>
     *
     * @param context
     */
    private void showCouponBottomSheet(final Context context) {
        final View view = getLayoutInflater().inflate(R.layout.coupon_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        final EditText editCouponCode = view.findViewById(R.id.edit_coupon_code);
        final ImageView imageDone = view.findViewById(R.id.image_done);
        final ProgressBar progressCoupon = view.findViewById(R.id.progress_coupon);

        imageDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageDone.setVisibility(View.GONE);
                progressCoupon.setVisibility(View.VISIBLE);

                management.sendRequestToServer(new RequestObject()
                        .setContext(getActivity())
                        .setJson(getCouponJson(editCouponCode.getText().toString(), placeId))
                        .setConnection(Constant.CONNECTION.REDEEM_COUPON)
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnectionCallback(new ConnectionCallback() {
                            @Override
                            public void onSuccess(Object data, RequestObject requestObject) {

                                progressCoupon.setVisibility(View.GONE);
                                imageDone.setVisibility(View.VISIBLE);

                                DataObject couponObject = (DataObject) data;
                                txtPromo.setText(couponObject.getCoupon_reward() + " % Off");

                                int totalBill = Integer.parseInt(Utility.extractNumericDataFromString(txtFairPrice.getText().toString()));
                                double discount = Double.parseDouble(couponObject.getCoupon_reward()) / 100.0;
                                int discountBill = (int) (totalBill * discount);
                                txtFairPrice.setText(currency_symbol + " " + discountBill);

                                if (bottomSheetDialog.isShowing())
                                    bottomSheetDialog.dismiss();

                            }

                            @Override
                            public void onError(String data, RequestObject requestObject) {
                                progressCoupon.setVisibility(View.GONE);
                                imageDone.setVisibility(View.VISIBLE);
                                Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                            }
                        }));

            }
        });


    }


    /**
     * <p>It is used to show Schedule Bottom Sheet</p>
     *
     * @param context
     */
    private void showScheduleBottomSheet(Context context) {


        final View view = getLayoutInflater().inflate(R.layout.schedule_order_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        final SingleDateAndTimePicker singleDateAndTimePicker = view.findViewById(R.id.time_date_selector);
        singleDateAndTimePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                Utility.Logger(TAG, "Date  = " + displayed);
                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                        .setDateObject(date)
                        .setCustomDateObject(true)
                        .setDatetimeType(Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss));

                selectedDate = dateTimeObject.getDatetime();
            }
        });

        TextView txtDone = view.findViewById(R.id.txt_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

    }


    /**
     * <p>It is used to set up marker</p>
     *
     * @param latitude
     * @param longitude
     */
    private void setUpMarker(double latitude, double longitude) {


        if (latitude == 0.0 || longitude == 0.0)
            return;

        LatLng position = new LatLng(latitude, longitude);
        if (googleMap == null)
            return;

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                position, 18);
        googleMap.animateCamera(location);

        //imageMarker.setVisibility(View.VISIBLE);


    }



}




