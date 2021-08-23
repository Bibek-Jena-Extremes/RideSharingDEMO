package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.eztruck.eztruckcustomer.AdapterUtil.LocationSelectorAdapter;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.InterfaceUtil.LocationCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.InternetObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfCity extends AppCompatActivity implements View.OnClickListener,
        ConnectionCallback, LocationCallback, TextView.OnEditorActionListener, TextWatcher {

    private String TAG = ListOfCity.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageClose;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewOrder;
    private LocationSelectorAdapter locationSelectorAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> backUpArraylist = new ArrayList<>();
    private EditText editSearch;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_city);

        getIntentData();  //Retrieve Intent Data
        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        city = getIntent().getStringExtra(Constant.IntentKey.SOURCE_CITY);
    }


    /**
     * <p>It initialize the UI</p>
     */


    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.select_city));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageClose = findViewById(R.id.image_close);
        editSearch = findViewById(R.id.edit_search);

        management = new Management(this);
        objectArrayList.add(new ProgressObject());


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

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson())
                .setConnection(Constant.CONNECTION.LIST_OF_CITY)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));


        imageBack.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        editSearch.addTextChangedListener(this);
        editSearch.setOnEditorActionListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }

        if (v == imageClose) {

            editSearch.setText(null);
            imageClose.setVisibility(View.GONE);
            objectArrayList.clear();
            objectArrayList.addAll(backUpArraylist);
            locationSelectorAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        DataObject dataObject = (DataObject) data;
        objectArrayList.clear();

        if (requestObject.getConnection() == Constant.CONNECTION.LIST_OF_CITY){

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                DataObject locationObject = dataObject.getObjectArrayList().get(i);
                if (locationObject.getDataType() == Constant.DATA_TYPE.CITY_VIEW){
                    if (locationObject.getCity_name().equalsIgnoreCase(city))
                        locationObject.setLocationSelected(true);
                    else
                        locationObject.setLocationSelected(false);
                }



                objectArrayList.add(locationObject);
            }

            backUpArraylist.addAll(objectArrayList);
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
        DataObject dataObject = (DataObject) objectArrayList.get(position);

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


    /**
     * <p>It trigger by Search & find required search terms</p>
     */

    private void performSearch(String searchText) {

        Utility.Logger(TAG, "Search : " + searchText);

        //For showing Progressing Animation

        Utility.hideKeyboard(this, editSearch);
        objectArrayList.clear();

        for (int i = 0; i < backUpArraylist.size(); i++) {

            DataObject dataObject = (DataObject) backUpArraylist.get(i);
            if ( !Utility.isEmptyString(dataObject.getCountry_name())
                    && dataObject.getCountry_name().equalsIgnoreCase(searchText)) {

                objectArrayList.add(dataObject);

            }else if (!Utility.isEmptyString(dataObject.getCity_name())
                    && dataObject.getCity_name().equalsIgnoreCase(searchText)){

                objectArrayList.add(dataObject);

            }
        }

        locationSelectorAdapter.notifyDataSetChanged();


    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson() {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "retrieve_all_countries");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


}
