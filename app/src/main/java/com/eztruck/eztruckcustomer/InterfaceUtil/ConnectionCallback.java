package com.eztruck.eztruckcustomer.InterfaceUtil;

import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;

public interface ConnectionCallback {

    void onSuccess(Object data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);


}
