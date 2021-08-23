package com.eztruck.eztruckcustomer.ActivityUtil;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.ChattingObject;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.DestinationObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RiderObject;
import com.eztruck.eztruckcustomer.ObjectUtil.TrackingObject;
import com.eztruck.eztruckcustomer.ObjectUtil.TypingObject;
import com.eztruck.eztruckcustomer.ObjectUtil.UserObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.MapUtils;
import com.eztruck.eztruckcustomer.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.model.JointType.ROUND;


public class TrackRider extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private String TAG = TrackRider.class.getSimpleName();
    private static final long ANIMATION_TIME_PER_ROUTE = 3000;
    private long DELAY = 4500;
    private DataObject dataObject;
    private Management management;
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageCancel;
    private DatabaseReference rootReference;
    private DatabaseReference riderRef;
    private DatabaseReference destinationRef;
    private DatabaseReference userRef;
    private DatabaseReference chatRef;
    private DatabaseReference statusRef;
    private DatabaseReference locationRef;
    private ValueEventListener riderEventListener;
    private ValueEventListener destinationEventListener;
    private ValueEventListener userEventListener;
    private ValueEventListener statusEventListener;
    private ValueEventListener locationEventListener;
    private ChildEventListener chatChildEventListener;
    private LinearLayout layoutCall;
    private LinearLayout layoutRider;
    private LinearLayout layoutCounter;
    private TextView txtCounter;
    private RoundedImageView imageProfile;
    private int messageCounter = 0;
    private TextView txtName;
    private TextView txtDistance;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker carMarker;
    private Handler handler;
    private LatLng startPosition;
    private LatLng endPosition;
    private double lat, lng;
    private float v;
    double latitude;
    double longitude;
    private boolean isFirstPosition = true;
    private UserObject userObject;
    private RiderObject riderObject;
    private PowerManager.WakeLock mWakeLock;
    private TextView txtDetail;
    private TextView txtCarNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_rider);

        getIntentData(); // Retrieve Intent Data
        awakeScreen(); //Activate Wake Lock Screen
        initUI();  // Initialize UI

    }


    /**
     * <p>It will make the screen be always on until this Activity gets destroyed</p>
     */
    @SuppressLint("InvalidWakeLockTag")
    private void awakeScreen() {
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.ORDER_DETAIL);
    }


    /**
     * <p>It is used to init UI</p>
     */
    private void initUI() {

        Utility.Logger(TAG, "Order ID = " + dataObject.getOrder_id());

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.track_rider));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageCancel = findViewById(R.id.image_filter);
        imageCancel.setImageResource(R.drawable.ic_close);
        imageCancel.setVisibility(View.VISIBLE);

        management = new Management(this);
        management.savePreferences(new PrefObject()
                .setSaveOrderId(true)
                .setOrderId(dataObject.getOrder_id()));


        imageProfile = findViewById(R.id.image_profile);
        txtName = findViewById(R.id.txt_name);
        txtDistance = findViewById(R.id.txt_distance);
        txtDetail = findViewById(R.id.txt_detail);
        txtCarNo = findViewById(R.id.txt_car_no);

        txtCounter = findViewById(R.id.txt_counter);
        layoutCall = findViewById(R.id.layout_call);
        layoutCounter = findViewById(R.id.layout_counter);
        layoutRider = findViewById(R.id.layout_rider);
        layoutRider.setVisibility(View.GONE);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        handler = new Handler();

        //database reference pointing to root of database
        rootReference = FirebaseDatabase.getInstance().getReference();

        userRef = rootReference.child("order").child(dataObject.getOrder_id()).child("userObject");
        userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "UserEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserObject value = dataSnapshot.getValue(UserObject.class);
                Utility.Logger(TAG, "Value is: " + value.toString());

                if (googleMap != null) {

                    googleMap.addMarker(new MarkerOptions().position(new LatLng(value.getUser_latitude(), value.getUser_longitude())).
                            flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_pointer)));

                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                            new LatLng(value.getUser_latitude(), value.getUser_longitude()), 18);
                    googleMap.animateCamera(location);

                }

                userObject = value;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        userRef.addValueEventListener(userEventListener);

        riderRef = rootReference.child("order").child(dataObject.getOrder_id()).child("riderObject");
        riderEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "RiderEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                RiderObject value = dataSnapshot.getValue(RiderObject.class);
                txtName.setText(value.getUser_name());
                ///txtDistance.setText(value.getDelivery_time());
                txtDetail.setText(value.getCar_colour()+" "+value.getCar_brand()+" "+value.getCar_name());
                txtCarNo.setText(value.getCar_number_plate());

                Glide.with(getApplicationContext()).load(Constant.ServerInformation.PICTURE_URL + value.getUser_picture())
                        .into(imageProfile);

                ///staticPolyLine();

                if (googleMap != null) {

                    googleMap.addMarker(new MarkerOptions().position(new LatLng(value.getRider_latitude(), value.getRider_longitude())).
                            flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_small_car)));

                }

                if (isFirstPosition) {
                    drawRouteOnMap(value.getDirection_route());
                }

                riderObject = value;
                layoutRider.setVisibility(View.VISIBLE);
                Utility.Logger(TAG, "Value is: " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        riderRef.addValueEventListener(riderEventListener);

        destinationRef = rootReference.child("order").child(dataObject.getOrder_id()).child("destinationObject");
        destinationEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "RiderEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DestinationObject value = dataSnapshot.getValue(DestinationObject.class);

                if (googleMap != null) {

                    googleMap.addMarker(new MarkerOptions().position(new LatLng(value.getLatitude(), value.getLongitude())).
                            flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_food_pointer)));

                }


                ///layoutRider.setVisibility(View.VISIBLE);
                Utility.Logger(TAG, "Value is: " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        destinationRef.addValueEventListener(destinationEventListener);

        locationRef = rootReference.child("order").child(dataObject.getOrder_id()).child("trackingObject");
        locationEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "LocationEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                TrackingObject value = dataSnapshot.getValue(TrackingObject.class);

                if (value.getStatus().equalsIgnoreCase("completed")) {
                    showRiderReviewSheet(TrackRider.this);
                }else if (value.getStatus().equalsIgnoreCase("cancel")) {

                    management.savePreferences(new PrefObject()
                            .setSaveOrderId(true)
                            .setOrderId("0"));

                    Utility.Toaster(getApplicationContext(),
                            Utility.getStringFromRes(getApplicationContext(),R.string.order_cancel),
                            Toast.LENGTH_LONG);

                    imageBack.callOnClick();

                }

                if (value.getLatitude() == 0.0)
                    return;

                Utility.Logger(TAG, "Location = " + value.toString());
                txtDistance.setText(value.getRemaining_distance() + " - " + value.getRemaining_duration());
                latitude = value.getLatitude();
                longitude = value.getLongitude();




                if (isFirstPosition) {
                    startGettingOnlineDataFromCar();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in LocationEvent = " + databaseError.toException());
            }
        };
        locationRef.addValueEventListener(locationEventListener);

        chatRef = rootReference.child("order").child(dataObject.getOrder_id()).child("userChattingObject");
        chatChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ChattingObject chattingObject = dataSnapshot.getValue(ChattingObject.class);
                if (!chattingObject.read)
                    return;

                if (chattingObject.from)
                    return;

                messageCounter++;
                txtCounter.setText(String.valueOf(messageCounter));
                layoutCounter.setVisibility(View.VISIBLE);

                Utility.Logger(TAG, "Counter = " + messageCounter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        statusRef = rootReference.child("order").child(dataObject.getOrder_id()).child("typingObject");
        statusEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "LocationEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                TypingObject value = dataSnapshot.getValue(TypingObject.class);
                Utility.Logger(TAG, "Location = " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in LocationEvent = " + databaseError.toException());
            }
        };
        statusRef.addValueEventListener(statusEventListener);

        ///riderRef.setValue(new RiderTrackingObject(Integer.parseInt(dataObject.getOrder_id()),0.0,0.0));


        imageBack.setOnClickListener(this);
        imageCancel.setOnClickListener(this);
        layoutCall.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            /*if (dataObject.isFromSplash()) {
            }*/
            startActivity(new Intent(getApplicationContext(), Base.class));
            finish();
        }
        if (v == layoutCall) {

            messageCounter = 0;
            layoutCounter.setVisibility(View.GONE);

            Intent intent = new Intent(getApplicationContext(), Chatting.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject.getOrder_id());
            intent.putExtra(Constant.IntentKey.USER, userObject);
            intent.putExtra(Constant.IntentKey.RIDER, riderObject);
            startActivity(intent);
        }
        if (v == imageCancel) {

            showCancelationAlertSheet(this);

        }
    }

    @Override
    protected void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();

        if (riderRef != null)
            riderRef.removeEventListener(riderEventListener);

        if (locationRef != null)
            locationRef.removeEventListener(locationEventListener);

        if (userRef != null)
            userRef.removeEventListener(userEventListener);

        if (destinationRef != null)
            destinationRef.removeEventListener(destinationEventListener);

        if (chatRef != null)
            chatRef.removeEventListener(chatChildEventListener);

        if (statusRef != null)
            statusRef.removeEventListener(statusEventListener);

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (chatRef != null && chatChildEventListener != null) {
            chatRef.addChildEventListener(chatChildEventListener);
            messageCounter = 0;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (chatRef != null && chatChildEventListener != null)
            chatRef.removeEventListener(chatChildEventListener);


    }


    /**
     * <p>Used to show bottom sheet dialog for Cart Alert</p>
     */
    private void showCancelationAlertSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.cart_alert_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        final TextView txtDone = view.findViewById(R.id.txt_done);
        TextView txtCancel = view.findViewById(R.id.txt_cancel);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                management.sendRequestToServer(new RequestObject()
                        .setContext(getApplicationContext())
                        .setLoadingText(Utility.getStringFromRes(getApplicationContext(), R.string.book_ride))
                        .setJson(getCancelOrderJson(dataObject.getOrder_id()))
                        .setConnection(Constant.CONNECTION.CANCEL_ORDER)
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnectionCallback(null));

                locationRef.child("status").setValue("cancel");

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();






            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });

    }


    /**
     * <p>It is used to initialize Car animation</p>
     *
     * @param startLatitude
     * @param startLongitude
     */
    private void animateCar(double startLatitude, double startLongitude) {
        Utility.Logger(TAG, startLatitude + "--" + startLongitude);

        if (isFirstPosition) {
            startPosition = new LatLng(startLatitude, startLongitude);

            carMarker = googleMap.addMarker(new MarkerOptions().position(startPosition).
                    flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_small_car)));
            carMarker.setAnchor(0.5f, 0.5f);

            googleMap.moveCamera(CameraUpdateFactory
                    .newCameraPosition
                            (new CameraPosition.Builder()
                                    .target(startPosition)
                                    .zoom(15.5f)
                                    .build()));

            isFirstPosition = false;

        } else {
            endPosition = new LatLng(startLatitude, startLongitude);
            Utility.Logger(TAG, startPosition.latitude + "--" + endPosition.latitude + "--Check --" + startPosition.longitude + "--" + endPosition.longitude);

            if ((startPosition.latitude != endPosition.latitude) || (startPosition.longitude != endPosition.longitude)) {

                Utility.Logger(TAG, "NOT SAME");
                startBikeAnimation(startPosition, endPosition);

            } else {

                Utility.Logger(TAG, "SAMME");
            }
        }

    }


    /**
     * <p>It is used to start Bike Animation</p>
     *
     * @param start
     * @param end
     */
    private void startBikeAnimation(final LatLng start, final LatLng end) {

        Utility.Logger(TAG, "startBikeAnimation called...");

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(ANIMATION_TIME_PER_ROUTE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                //LogMe.i(TAG, "Car Animation Started...");
                v = valueAnimator.getAnimatedFraction();
                lng = v * end.longitude + (1 - v)
                        * start.longitude;
                lat = v * end.latitude + (1 - v)
                        * start.latitude;

                LatLng newPos = new LatLng(lat, lng);
                carMarker.setPosition(newPos);
                carMarker.setAnchor(0.5f, 0.5f);
                carMarker.setRotation(MapUtils.getBearing(start, end));

                // todo : Shihab > i can delay here
                googleMap.moveCamera(CameraUpdateFactory
                        .newCameraPosition
                                (new CameraPosition.Builder()
                                        .target(newPos)
                                        .zoom(15.5f)
                                        .build()));

                startPosition = carMarker.getPosition();

            }

        });
        valueAnimator.start();


    }


    /**
     * <p>It is used to draw Route on Map
     * between source & destination</p>
     */
    private void drawRouteOnMap(String geometry) {

        if (Utility.isEmptyString(geometry))
            return;

        LineString lineString = LineString.fromPolyline(geometry, Constants.PRECISION_6);
        List<LatLng> polyLineList = new ArrayList<>();

        for (int i = 0; i < lineString.coordinates().size(); i++) {

            double latitude = lineString.coordinates().get(i).latitude();
            double longitude = lineString.coordinates().get(i).longitude();
            LatLng location = new LatLng(latitude, longitude);
            polyLineList.add(location);

        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(polyLineList);

        if (googleMap != null)
            googleMap.addPolyline(polylineOptions);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(false);


    }

    /**
     * <p>It is used to get location updates
     * from Server</p>
     */
    private void startGettingOnlineDataFromCar() {
        handler.post(mStatusChecker);
    }

    /**
     * <p>Used to show bottom sheet dialog for Adding Review to Book</p>
     */
    private void showRiderReviewSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_review_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        management.savePreferences(new PrefObject()
                .setSaveOrderId(true)
                .setOrderId("0"));

        ///finish();


        final RatingBar smileRating = view.findViewById(R.id.smile_rating);
        final EditText txtReview = view.findViewById(R.id.txt_review);
        final RelativeLayout layoutReview = view.findViewById(R.id.layout_review);
        final ImageView imageAttach = view.findViewById(R.id.image_attach);
        TextView txtSubmit = view.findViewById(R.id.txt_submit);

        smileRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                layoutReview.setVisibility(View.VISIBLE);
            }
        });


        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                management.sendRequestToServer(new RequestObject()
                        .setJson(getRiderRatingJson(dataObject.getOrder_id(), String.valueOf(riderObject.getUser_id())
                                , String.valueOf(smileRating.getRating()), txtReview.getText().toString()))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.ADD_RIDER_RATING)
                        .setConnectionCallback(null));

                finish();



            }
        });

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getRiderRatingJson(String order_id, String captain_id, String rating, String review) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_rider_rating");
            jsonObject.accumulate("order_id", order_id);
            jsonObject.accumulate("captain_id", captain_id);
            jsonObject.accumulate("rating", rating);
            jsonObject.accumulate("review", review);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {

                animateCar(latitude, longitude);


            } catch (Exception e) {
                Utility.Logger(TAG, e.getMessage());
            }
            handler.postDelayed(mStatusChecker, DELAY);

        }
    };



    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getCancelOrderJson(String order_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "cancel_order");
            jsonObject.accumulate("order_id", order_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


}




