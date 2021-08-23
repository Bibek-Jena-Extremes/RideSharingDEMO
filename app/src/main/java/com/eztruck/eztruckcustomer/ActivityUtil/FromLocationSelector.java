package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.eztruck.eztruckcustomer.AdapterUtil.LocationSelectorAdapter;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.InterfaceUtil.LocationCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.InternetObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FromLocationSelector extends AppCompatActivity implements View.OnClickListener,
        ConnectionCallback, LocationCallback, TextView.OnEditorActionListener, TextWatcher {

    private String TAG = FromLocationSelector.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageClose;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewOrder;
    private LocationSelectorAdapter locationSelectorAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> backUpArraylist = new ArrayList<>();
    private PrefObject prefObject;
    private LinearLayout layoutLocationMap;
    private LinearLayout layoutCity;
    private TextView txtCity;
    private boolean toLocation;
    private double sourceLatitude;
    private double sourceLongitude;
    private EditText editSearch;
    private boolean saveBackUp = true;
    private String city;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_location_selector);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {

        toLocation = getIntent().getBooleanExtra(Constant.IntentKey.LOCATION_SELECTOR, false);
        city = getIntent().getStringExtra(Constant.IntentKey.SOURCE_CITY);

        if (!toLocation) {
            sourceLatitude = getIntent().getDoubleExtra(Constant.IntentKey.SOURCE_LATITUDE, 0.0);
            sourceLongitude = getIntent().getDoubleExtra(Constant.IntentKey.SOURCE_LONGITUDE, 0.0);
        }

    }


    /**
     * <p>It initialize the UI</p>
     */


    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.search_location));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        layoutCity = findViewById(R.id.layout_city);
        txtCity = findViewById(R.id.txt_city);

        imageClose = findViewById(R.id.image_close);
        editSearch = findViewById(R.id.edit_search);

        layoutLocationMap = findViewById(R.id.layout_location_map);
        if (toLocation) {
            layoutLocationMap.setVisibility(View.VISIBLE);
            layoutCity.setVisibility(View.GONE);
        } else {
            txtCity.setText(city);
        }

        //objectArrayList.add(new ProgressObject());
        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));


        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                } else {
                    return 1;
                }
            }
        });

        recyclerViewOrder = findViewById(R.id.recycler_view_categories);
        recyclerViewOrder.setLayoutManager(gridLayoutManager);

        //Initialize & Setup Adapter with Recycler View

        locationSelectorAdapter = new LocationSelectorAdapter(this, objectArrayList, this);
        recyclerViewOrder.setAdapter(locationSelectorAdapter);

        if (!toLocation) {

            management.sendRequestToServer(new RequestObject()
                    .setSourceCoordinates(String.valueOf(sourceLatitude))
                    .setDestinationCoordinates(String.valueOf(sourceLongitude))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.NEARBY_LOCATIONS)
                    .setConnectionCallback(this));

        }


        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCCUsQlk5QayXYAxdNd1zigS57qhyBvowM");

        // Create a new Places client instance.
        placesClient = Places.createClient(this);


        imageBack.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        layoutCity.setOnClickListener(this);
        layoutLocationMap.setOnClickListener(this);
        editSearch.addTextChangedListener(this);
        editSearch.setOnEditorActionListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v == layoutLocationMap) {
            finish();
        }
        if (v == imageClose) {

            editSearch.setText(null);
            imageClose.setVisibility(View.GONE);
            objectArrayList.clear();
            objectArrayList.addAll(backUpArraylist);
            locationSelectorAdapter.notifyDataSetChanged();

        }
        if (v == layoutCity) {

            Intent intent = new Intent(this, ListOfCity.class);
            intent.putExtra(Constant.IntentKey.SOURCE_CITY, txtCity.getText().toString());
            startActivityForResult(intent, Constant.RequestCode.CITY_SELECTOR_REQUEST_CODE);

        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        DataObject dataObject = (DataObject) data;

        if (requestObject.getConnection() == Constant.CONNECTION.NEARBY_LOCATIONS) {

            ///objectArrayList.clear();
            objectArrayList.add(new DataObject()
                    .setLocation_label(Utility.getStringFromRes(this, R.string.nearby_location))
                    .setDataType(Constant.DATA_TYPE.LABEL_VIEW));

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                objectArrayList.add(dataObject.getObjectArrayList().get(i));
            }

            if (saveBackUp) {
                backUpArraylist.addAll(objectArrayList);
                saveBackUp = false;
            }

            locationSelectorAdapter.notifyDataSetChanged();

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.AUTO_COMPLETE) {

            objectArrayList.clear();
            objectArrayList.add(new DataObject()
                    .setLocation_label(Utility.getStringFromRes(this, R.string.search_result))
                    .setDataType(Constant.DATA_TYPE.LABEL_VIEW));

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                objectArrayList.add(dataObject.getObjectArrayList().get(i));
            }

            locationSelectorAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Setting = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.no_order))
                .setDescription(Utility.getStringFromRes(this, R.string.no_order_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_order));
        //orderAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLocationSelectedListener(int position) {
        final DataObject dataObject = (DataObject) objectArrayList.get(position);

        if (dataObject.getDataType() == Constant.DATA_TYPE.AUTO_COMPLETE_VIEW) {

            // Specify the fields to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG);

            // Construct a request object, passing the place ID and fields array.
            FetchPlaceRequest request = FetchPlaceRequest.newInstance(dataObject.getPlace_id(), placeFields);

            placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                @Override
                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                    Place place = fetchPlaceResponse.getPlace();
                    Utility.Logger(TAG, "Place found: Latitude = " +
                            place.getLatLng().latitude + " Longitude = " + place.getLatLng().longitude);

                    Intent intent = new Intent();
                    intent.putExtra(Constant.IntentKey.LOCATION_SELECTOR, dataObject.setLocation_latitude(place.getLatLng().latitude)
                            .setLocation_longitude(place.getLatLng().longitude));
                    setResult(RESULT_OK, intent);
                    finish();


                }

            });

            return;

        }


        Intent intent = new Intent();
        intent.putExtra(Constant.IntentKey.LOCATION_SELECTOR, dataObject);
        setResult(RESULT_OK, intent);
        finish();


    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!Utility.isEmptyString(s.toString())) {
            imageClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.CITY_SELECTOR_REQUEST_CODE && resultCode == RESULT_OK) {
            DataObject cityObject = data.getParcelableExtra(Constant.IntentKey.LOCATION_SELECTOR);
            txtCity.setText(cityObject.getCity_name());
            city = cityObject.getCity_name();
        }

    }

    /**
     * <p>It trigger by Search & find required search terms</p>
     */

    private void performSearch(String searchText) {

        Constant.CONNECTION connection = null;
        Utility.Logger(TAG, "Search : " + searchText);

        //For showing Progressing Animation

        Utility.hideKeyboard(this, editSearch);

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        locationSelectorAdapter.notifyDataSetChanged();

        //Send Request for searching of specific location

        management.sendRequestToServer(new RequestObject()
                .setSearchedLocation(searchText)
                .setCity(city)
                .setSourceCoordinates(String.valueOf(sourceLatitude))
                .setDestinationCoordinates(String.valueOf(sourceLongitude))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.AUTO_COMPLETE)
                .setConnectionCallback(this));


    }


}
