package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.DestinationObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RiderObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class RideDetail extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private String TAG = RideDetail.class.getSimpleName();
    private TextView txtMenu;
    private ImageView imageBack;
    private MapView mapView;
    private TextView txtRideFare;
    private TextView txtDuration;
    private TextView txtDistance;
    private TextView txtPayment;
    private TextView txtCaptainName;
    private RatingBar ratingBar;
    private TextView txtCarDetail;
    private TextView txtCardNo;
    private LinearLayout layoutProblem;
    private GoogleMap googleMap;
    private DatabaseReference rootReference;
    private DatabaseReference riderRef;
    private ValueEventListener riderEventListener;
    private DatabaseReference destinationRef;
    private ValueEventListener destinationEventListener;
    private DataObject dataObject;
    private ImageView imageCaptain;
    private DestinationObject destinationObject;
    private Management management;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        getIntentData(); //Retrieve Intent Data
        initUI(savedInstanceState); //Initialize UI

    }


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.ORDER_DETAIL);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(Bundle savedInstanceState) {

        management = new Management(this);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.ride_detail));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        layoutProblem = findViewById(R.id.layout_problem);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();   // needed to get the map to display immediately
        mapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rootReference = FirebaseDatabase.getInstance().getReference();

        destinationRef = rootReference.child("order").child(dataObject.getOrder_id()).child("destinationObject");
        destinationEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "RiderEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DestinationObject value = dataSnapshot.getValue(DestinationObject.class);
                destinationObject = value;
                Utility.Logger(TAG, "Value is: " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        destinationRef.addValueEventListener(destinationEventListener);

        riderRef = rootReference.child("order").child(dataObject.getOrder_id()).child("riderObject");
        riderEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "RiderEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                RiderObject value = dataSnapshot.getValue(RiderObject.class);
                ///drawRouteOnMap(value.getDirection_route());
                Utility.Logger(TAG, "Value is: " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        riderRef.addValueEventListener(riderEventListener);


        txtRideFare = findViewById(R.id.txt_ride_fare);
        txtDuration = findViewById(R.id.txt_duration);
        txtDistance = findViewById(R.id.txt_distance);
        txtPayment = findViewById(R.id.txt_payment);
        txtCaptainName = findViewById(R.id.txt_captain_name);
        txtCarDetail = findViewById(R.id.txt_car_detail);
        txtCardNo = findViewById(R.id.txt_card_no);
        imageCaptain = findViewById(R.id.image_captain);

        ratingBar = findViewById(R.id.rating_bar);
        layoutProblem = findViewById(R.id.layout_problem);

        txtRideFare.setText(dataObject.getRide_fare());
        txtDuration.setText(dataObject.getRide_duration());
        txtDistance.setText(dataObject.getRide_distance());
        txtPayment.setText(dataObject.getRide_payment_type());
        txtCaptainName.setText(dataObject.getRide_captain_name());
        txtCarDetail.setText(dataObject.getRide_captain_car_colour() + " " + dataObject.getRide_captain_car_brand() + " "
                + dataObject.getRide_captain_car_name() + " (" + dataObject.getRide_captain_ride_type() + ")");

        if (!Utility.isEmptyString(dataObject.getRating()))
            ratingBar.setRating(Float.parseFloat(dataObject.getRating()));
        else
            ratingBar.setRating(0f);

        txtCardNo.setText(dataObject.getRide_captain_number_plate());

        Glide.with(this).load(Constant.ServerInformation.PICTURE_URL + dataObject.getRide_captain_picture())
                .into(imageCaptain);


        imageBack.setOnClickListener(this);
        layoutProblem.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v==layoutProblem){
            showRiderReviewSheet(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng sourceLocation = new LatLng(Double.parseDouble(dataObject.getHistory_from_latitude()), Double.parseDouble(dataObject.getHistory_from_longitude()));
        LatLng destinationLocation = new LatLng(Double.parseDouble(dataObject.getHistory_to_latitude()), Double.parseDouble(dataObject.getHistory_to_longitude()));

        /*this.googleMap.addMarker(new MarkerOptions().position(sourceLocation).
                flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_small_car)));*/

        this.googleMap.addMarker(new MarkerOptions().position(destinationLocation).
                flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_pointer_bold)));


        final CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(destinationLocation,15);
        googleMap.animateCamera(cu);

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

        /**create the bounds from latlngBuilder to set into map camera*/
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < lineString.coordinates().size(); i++) {

            double latitude = lineString.coordinates().get(i).latitude();
            double longitude = lineString.coordinates().get(i).longitude();
            LatLng location = new LatLng(latitude, longitude);
            builder.include(new LatLng(location.latitude, location.longitude));
            polyLineList.add(location);

        }

        LatLngBounds bounds = builder.build();

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(polyLineList);

        if (googleMap != null) {
            googleMap.addPolyline(polylineOptions);

            if (destinationObject==null)
                return;

            final CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(destinationObject.getLatitude(),destinationObject.getLongitude()),15);
            googleMap.animateCamera(cu);

        }




    }



    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String report) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.accumulate("functionality", "report_ride");
            jsonObject.accumulate("order_id", dataObject.getOrder_id());
            jsonObject.accumulate("report", report);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }



    /**
     * <p>Used to show bottom sheet dialog for Adding Review to Book</p>
     */
    private void showRiderReviewSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_report_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        final EditText editReview = view.findViewById(R.id.edit_review);
        TextView txtSubmit = view.findViewById(R.id.txt_submit);


        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                management.sendRequestToServer(new RequestObject()
                        .setJson(getJson(editReview.getText().toString()))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.ADD_REPORT)
                        .setConnectionCallback(null));

                finish();



            }
        });

    }




}
