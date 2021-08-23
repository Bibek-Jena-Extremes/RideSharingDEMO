package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.InterfaceUtil.ConnectionCallback;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;


import org.json.JSONException;
import org.json.JSONObject;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    private String TAG = Setting.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private PrefObject prefObject;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtPhone;
    private LinearLayout layoutName;
    private LinearLayout layoutEmail;
    private LinearLayout layoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.setting));

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPhone = findViewById(R.id.txt_phone);

        layoutName = findViewById(R.id.layout_name);
        layoutEmail = findViewById(R.id.layout_email);
        layoutPassword = findViewById(R.id.layout_password);

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        txtName.setText(prefObject.getFirstName() + " " + prefObject.getLastName());
        txtEmail.setText(prefObject.getUserEmail());
        txtPhone.setText(prefObject.getUserPhone());

        layoutName.setOnClickListener(this);
        layoutEmail.setOnClickListener(this);
        layoutPassword.setOnClickListener(this);
        imageBack.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v == layoutName) {
            showProfileBottomSheet(this, true, false, false);

        }
        if (v == layoutEmail) {
            showProfileBottomSheet(this, false, true, false);
        }

        if (v == layoutPassword) {
            showProfileBottomSheet(this, false, false, true);
        }

        /*if (v == layoutPrivacy) {
          startActivity(new Intent(getActivity(), AboutUs.class));
        }*/

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * <p>It is used to show Payment Selection</p>
     *
     * @param context
     */
    private void showProfileBottomSheet(final Context context, boolean isName, boolean isEmail, boolean isPassword) {

        int layout = 0;

        if (isName) {
            layout = R.layout.update_name_bottom_sheet;
        }
        else if (isEmail) {
            layout = R.layout.update_email_bottom_sheet;
        }
        else if (isPassword) {
            layout = R.layout.update_password_bottom_sheet;
        }

        final View view = getLayoutInflater().inflate(layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        if (isName) {

            final EditText editFirstName = view.findViewById(R.id.edit_first_name);
            final EditText editLastName = view.findViewById(R.id.edit_last_name);
            final ImageView imageDone = view.findViewById(R.id.image_done);
            final ProgressBar progressCoupon = view.findViewById(R.id.progress_coupon);

            String[] name = txtName.getText().toString().split("\\s+");

            editFirstName.setText(name[0]);
            editLastName.setText(name[1]);

            imageDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imageDone.setVisibility(View.GONE);
                    progressCoupon.setVisibility(View.VISIBLE);

                    management.sendRequestToServer(new RequestObject()
                            .setContext(context)
                            .setJson(getJson("update_name", editFirstName.getText().toString(), editLastName.getText().toString(), null, null))
                            .setConnection(Constant.CONNECTION.UPDATE)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnectionCallback(new ConnectionCallback() {
                                @Override
                                public void onSuccess(Object data, RequestObject requestObject) {

                                    progressCoupon.setVisibility(View.GONE);
                                    imageDone.setVisibility(View.VISIBLE);

                                    DataObject couponObject = (DataObject) data;
                                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.updated_successfully), Toast.LENGTH_SHORT);
                                    txtName.setText(editFirstName.getText().toString() + " " + editLastName.getText().toString());
                                    management.savePreferences(new PrefObject()
                                            .setSaveUserCredential(true)
                                            .setFirstName(editFirstName.getText().toString())
                                            .setLastName(editLastName.getText().toString()));

                                    if (bottomSheetDialog.isShowing())
                                        bottomSheetDialog.dismiss();

                                }

                                @Override
                                public void onError(String data, RequestObject requestObject) {
                                    progressCoupon.setVisibility(View.GONE);
                                    imageDone.setVisibility(View.VISIBLE);
                                    Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                                }
                            }));

                }
            });

        }
        else if (isEmail) {

            final EditText editEmail = view.findViewById(R.id.edit_email);
            final ImageView imageDone = view.findViewById(R.id.image_done);
            final ProgressBar progressCoupon = view.findViewById(R.id.progress_coupon);

            editEmail.setText(prefObject.getUserEmail());

            imageDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imageDone.setVisibility(View.GONE);
                    progressCoupon.setVisibility(View.VISIBLE);

                    management.sendRequestToServer(new RequestObject()
                            .setContext(context)
                            .setJson(getJson("update_email", null, null, editEmail.getText().toString(), null))
                            .setConnection(Constant.CONNECTION.UPDATE)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnectionCallback(new ConnectionCallback() {
                                @Override
                                public void onSuccess(Object data, RequestObject requestObject) {

                                    progressCoupon.setVisibility(View.GONE);
                                    imageDone.setVisibility(View.VISIBLE);

                                    DataObject couponObject = (DataObject) data;
                                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.updated_successfully), Toast.LENGTH_SHORT);
                                    txtEmail.setText(editEmail.getText().toString());

                                    management.savePreferences(new PrefObject()
                                            .setSaveUserCredential(true)
                                            .setUserEmail(editEmail.getText().toString()));

                                    if (bottomSheetDialog.isShowing())
                                        bottomSheetDialog.dismiss();

                                }

                                @Override
                                public void onError(String data, RequestObject requestObject) {
                                    progressCoupon.setVisibility(View.GONE);
                                    imageDone.setVisibility(View.VISIBLE);
                                    Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                                }
                            }));

                }
            });


        }
        else if (isPassword) {

            final EditText editPassword = view.findViewById(R.id.edit_password);
            final EditText editNewPassword = view.findViewById(R.id.edit_new_password);
            final EditText editConfirmPassword = view.findViewById(R.id.edit_confirm_password);
            final TextView txtDone = view.findViewById(R.id.txt_done);
            final LinearLayout layoutDone = view.findViewById(R.id.layout_done);
            final ProgressBar progressCoupon = view.findViewById(R.id.progress_coupon);

            layoutDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!prefObject.getUserPassword().equalsIgnoreCase(editPassword.getText().toString())) {
                        Utility.Toaster(context, Utility.getStringFromRes(context, R.string.incorrect_current_password), Toast.LENGTH_SHORT);
                        return;
                    }

                    if (!editNewPassword.getText().toString().equals(editConfirmPassword.getText().toString())) {
                        Utility.Toaster(context, Utility.getStringFromRes(context, R.string.incorrect_both_password), Toast.LENGTH_SHORT);
                        return;
                    }

                    txtDone.setVisibility(View.GONE);
                    progressCoupon.setVisibility(View.VISIBLE);

                    management.sendRequestToServer(new RequestObject()
                            .setContext(context)
                            .setJson(getJson("update_password", null, null, null, editNewPassword.getText().toString()))
                            .setConnection(Constant.CONNECTION.UPDATE)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnectionCallback(new ConnectionCallback() {
                                @Override
                                public void onSuccess(Object data, RequestObject requestObject) {

                                    progressCoupon.setVisibility(View.GONE);
                                    txtDone.setVisibility(View.VISIBLE);

                                    DataObject couponObject = (DataObject) data;
                                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.updated_successfully), Toast.LENGTH_SHORT);

                                    management.savePreferences(new PrefObject()
                                            .setSaveUserCredential(true)
                                            .setUserPassword(editNewPassword.getText().toString()));


                                    if (bottomSheetDialog.isShowing())
                                        bottomSheetDialog.dismiss();

                                }

                                @Override
                                public void onError(String data, RequestObject requestObject) {
                                    progressCoupon.setVisibility(View.GONE);
                                    txtDone.setVisibility(View.VISIBLE);
                                    Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                                }
                            }));

                }
            });


        }


    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getJson(String functionality, String first_name, String last_name, String email, String password) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);

            if (!Utility.isEmptyString(first_name)) {

                jsonObject.accumulate("first_name", first_name);
                jsonObject.accumulate("last_name", last_name);

            }

            if (!Utility.isEmptyString(email)) {

                jsonObject.accumulate("email", email);

            }

            if (!Utility.isEmptyString(password)) {

                jsonObject.accumulate("password", password);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


}
