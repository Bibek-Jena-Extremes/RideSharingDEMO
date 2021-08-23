package com.eztruck.eztruckcustomer.ServiceUtil;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.google.gson.Gson;
import com.eztruck.eztruckcustomer.ConnectionUtil.Connection;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.GlobalDataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.util.ArrayList;

public class OreoIntentService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private String TAG = OreoIntentService.class.getName();

    public OreoIntentService() {
    }

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {

        enqueueWork(context, OreoIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Utility.Logger(TAG, "Setting : Working");

        if (intent != null) {


            //It load specific tags wallpaper at background
            //After loading it would add them into Wallpaper db

            RequestObject requestObject = GlobalDataObject.getRequestObject() != null ? GlobalDataObject.getRequestObject()
                    : (RequestObject) intent.getParcelableExtra(Constant.IntentKey.REQUEST_OBJECT);

            String result = Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());

            GlobalDataObject.requestObject = null;

            Utility.Logger(TAG, "JSON = " + requestObject.getJson());

            if (Utility.isEmptyString(result))
                return;

            if (result.equalsIgnoreCase(Constant.ImportantMessages.CONNECTION_ERROR))
                return;

            Gson gson = new Gson();
            Object object = null;
            DataObject dataObject = null;
            ArrayList<Object> objectList = new ArrayList<>();



            if (dataObject == null)
                return;


        }

    }


}
