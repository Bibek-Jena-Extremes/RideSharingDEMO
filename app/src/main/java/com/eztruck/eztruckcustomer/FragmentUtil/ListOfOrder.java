package com.eztruck.eztruckcustomer.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eztruck.eztruckcustomer.ActivityUtil.RideDetail;
import com.eztruck.eztruckcustomer.AdapterUtil.OrderAdapter;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.InterfaceUtil.OrderCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.InternetObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfOrder extends Fragment implements View.OnClickListener, ConnectionCallback, OrderCallback {
    private String TAG = ListOfOrder.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewOrder;
    private OrderAdapter orderAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private PrefObject prefObject;
    private String historyType;


    public static Fragment getOrderInstance(String feature) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.RIDE_HISTORY, feature);
        Fragment fragment = new ListOfOrder();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); // Retrieve Intent Data
        initUI(view); //Initialize UI

    }


    private void getIntentData(){
        historyType =  getArguments().getString(Constant.IntentKey.RIDE_HISTORY);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        management = new Management(getActivity());

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));


        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                }
                else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                }
                else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                }
                else {
                    return 1;
                }
            }
        });

        recyclerViewOrder = view.findViewById(R.id.recycler_view_categories);
        recyclerViewOrder.setLayoutManager(gridLayoutManager);

        //Initialize & Setup Adapter with Recycler View

        orderAdapter = new OrderAdapter(getActivity(), objectArrayList, this);
        recyclerViewOrder.setAdapter(orderAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ORDER_HISTORY)
                .setConnectionCallback(this));


        ////imageBack.setOnClickListener(this);

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

            if (historyType.equalsIgnoreCase("schedule")){

                jsonObject.accumulate("functionality", "order_schedule");

            }else if (historyType.equalsIgnoreCase("history")){

                jsonObject.accumulate("functionality", "order_history");

            }

            jsonObject.accumulate("user_id", prefObject.getUserId());  ///TODO User ID


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                objectArrayList.add(dataObject.getObjectArrayList().get(i));
            }

            orderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Setting = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_order))
                .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_order_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_order));
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOrderClickListener(int position) {
        DataObject dataObject = (DataObject) objectArrayList.get(position);

        if (historyType.equalsIgnoreCase("history")){

            Intent intent = new Intent(getActivity(), RideDetail.class);
            intent.putExtra(Constant.IntentKey.ORDER_DETAIL,dataObject);
            startActivity(intent);

        }






    }

}
