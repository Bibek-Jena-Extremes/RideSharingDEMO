package com.eztruck.eztruckcustomer.ActivityUtil;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.EditTextUtil.UbuntuRegularEditText;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.TextviewUtil.UbuntuLightTextview;
import com.eztruck.eztruckcustomer.TextviewUtil.UbuntuMediumTextview;
import com.eztruck.eztruckcustomer.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private TextView appName;
    private TextView txtInformation;
    private EditText editEmail;
    private TextView txtForgot;
    private Management management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUI(); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.forgot_password));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        appName = findViewById(R.id.app_name);
        txtInformation = (UbuntuLightTextview) findViewById(R.id.txt_information);
        editEmail = (UbuntuRegularEditText) findViewById(R.id.edit_email);
        txtForgot = (UbuntuMediumTextview) findViewById(R.id.txt_forgot);

        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    /**
     * <p>It is used to send request to server for userObject authentication</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.FORGOT)
                .setConnectionCallback(this));
    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "forgot_password");
            jsonObject.accumulate("email", editEmail.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }

    @Override
    public void onClick(View v) {
        if (v == txtForgot) {

            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_LONG);
                return;
            }

            sendServerRequest();

        }
        if (v == imageBack) {
            finish();
        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject != null) {

            Utility.Toaster(this,Utility.getStringFromRes(this,R.string.password_email_to_yuo),Toast.LENGTH_SHORT);
            editEmail.setText(null);

        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this,data,Toast.LENGTH_SHORT);
    }

}
