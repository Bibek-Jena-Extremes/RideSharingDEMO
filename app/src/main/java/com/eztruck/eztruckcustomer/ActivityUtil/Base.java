package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.DrawerUtils.NavDrawerAdapter;
import com.eztruck.eztruckcustomer.DrawerUtils.NavDrawerItem;
import com.eztruck.eztruckcustomer.FragmentUtil.HomeFragment;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Base extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener , ConnectionCallback {
    private PrefObject prefObject;
    private Management management;
    private ListView listViewDrawer;
    private ArrayList<NavDrawerItem> navDrawerItemArrayList = new ArrayList<>();
    private NavDrawerAdapter navDrawerAdapter;
    private String TAG = Base.class.getName();
    public static DrawerLayout layoutDrawer;
    private TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveOrderId(true)
                .setRetrieveUserCredential(true));

        initDrawerData();

        listViewDrawer = findViewById(R.id.listview_drawer);
        navDrawerAdapter = new NavDrawerAdapter(this, navDrawerItemArrayList);
        listViewDrawer.setAdapter(navDrawerAdapter);

        View listHeaderView = getLayoutInflater().inflate(R.layout.drawer_header, null, false);
        txtName = listHeaderView.findViewById(R.id.txt_name);
        txtName.setText(prefObject.getFirstName()+" "+prefObject.getLastName());
        listViewDrawer.addHeaderView(listHeaderView);




        Utility.Logger(TAG, "Generating Hash Key....");
        //Utility.printHashKeyForFacebook(this);  //For getting Facebook Hash Key

        layoutDrawer = findViewById(R.id.layout_drawer);


        openFragment(new HomeFragment());

        listViewDrawer.setOnItemClickListener(this);

    }


    /**
     * <p>It is used to initialize Drawer Data</p>
     */
    private void initDrawerData() {

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.current_ride), R.drawable.ic_menu_current_ride
                , false, "0"));

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.your_rides), R.drawable.ic_menu_schedule
                , false, "0"));

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.app_setting), R.drawable.ic_menu_setting
                , false, "0"));

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.share), R.drawable.ic_menu_share
                , false, "0"));

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.rate), R.drawable.ic_rating
                , false, "0"));

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.privacy_policy), R.drawable.ic_menu_policy
                , false, "0"));

        navDrawerItemArrayList.add(new NavDrawerItem(Utility.getStringFromRes(this, R.string.logout), R.drawable.ic_menu_logout
                , false, "0"));


    }


    /**
     * <p>It is used to open Fragment</p>
     *
     * @param fragment
     */
    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

        }
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onBackPressed() {
        ///super.onBackPressed();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) {

        } else if (position == 1) {

            if (Integer.parseInt(prefObject.getOrderId()) > 0) {

                Intent intent = new Intent(getApplicationContext(), TrackRider.class);
                intent.putExtra(Constant.IntentKey.ORDER_DETAIL, new DataObject()
                        .setOrder_id(prefObject.getOrderId())
                        .setFromSplash(true));
                startActivity(intent);

            } else {

                management.sendRequestToServer(new RequestObject()
                        .setContext(this)
                        .setLoadingText(Utility.getStringFromRes(this,R.string.check_ride))
                        .setJson(getJson(prefObject.getUserId()))
                        .setConnection(Constant.CONNECTION.CURRENT_RIDE)
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnectionCallback(this));

            }

            layoutDrawer.closeDrawer(Gravity.LEFT);

        }
        else if (position == 2) {
            startActivity(new Intent(this, History.class));
            layoutDrawer.closeDrawer(Gravity.LEFT);
        }
        else if (position == 3) {
            startActivity(new Intent(this, Setting.class));
            layoutDrawer.closeDrawer(Gravity.LEFT);
        }
        else if (position == 4) {
            Utility.shareApp(this);
            Utility.Logger(TAG,"Share with others");
            layoutDrawer.closeDrawer(Gravity.LEFT);
        }
        else if (position == 5) {
            Utility.rateApp(this);
            Utility.Logger(TAG,"Rate with others");
            layoutDrawer.closeDrawer(Gravity.LEFT);
        }
        else if (position == 6) {
            startActivity(new Intent(getApplicationContext(),AboutUs.class));
            layoutDrawer.closeDrawer(Gravity.LEFT);
        }
        else if (position == 7) {

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(false));

            startActivity(new Intent(this,OnBoarding.class));
            finish();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveOrderId(true)
                .setRetrieveUserCredential(true));

        txtName.setText(prefObject.getFirstName()+" "+prefObject.getLastName());

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (requestObject.getConnection() == Constant.CONNECTION.CURRENT_RIDE){

            DataObject orderObject = (DataObject) data;
            Intent intent = new Intent(getApplicationContext(), TrackRider.class);
            intent.putExtra(Constant.IntentKey.ORDER_DETAIL, orderObject);
            startActivity(intent);

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        if (requestObject.getConnection() == Constant.CONNECTION.CURRENT_RIDE){
            Utility.Toaster(getApplicationContext(), Utility.getStringFromRes(this, R.string.no_ride_in_progress), Toast.LENGTH_SHORT);
        }
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getJson(String user_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "current_ride");
            jsonObject.accumulate("user_id", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


}
