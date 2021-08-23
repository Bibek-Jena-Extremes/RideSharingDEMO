package com.eztruck.eztruckcustomer.InterfaceUtil;

import android.net.Uri;

import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;

public interface DatabaseCallback {

    void onSuccess(Uri data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);

}
