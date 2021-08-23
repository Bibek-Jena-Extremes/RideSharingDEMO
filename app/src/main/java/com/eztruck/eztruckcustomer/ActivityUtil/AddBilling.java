package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import com.eztruck.eztruckcustomer.MyApplication;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;



import org.json.JSONException;
import org.json.JSONObject;

public class AddBilling extends AppCompatActivity implements View.OnClickListener {

    private String TAG = AddBilling.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private TextView txtDone;
    private EditText editCardHolder;
    private EditText editCardNumber;
    private EditText editExpiryMonth;
    private EditText editExpiryYear;
    private EditText editCVV;
    private ProgressBar progressBar;
    private LinearLayout layoutDone;
    private Management management;
    private PrefObject prefObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_billing);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.add_billing));

        txtDone = findViewById(R.id.txt_done);
        editCardHolder = findViewById(R.id.edit_card_holder);
        editCardNumber = findViewById(R.id.edit_card_number);
        editExpiryMonth = findViewById(R.id.edit_expiry_month);
        editExpiryYear = findViewById(R.id.edit_expiry_year);
        editCVV = findViewById(R.id.edit_cvv);

        progressBar = findViewById(R.id.progress_bar);
        layoutDone = findViewById(R.id.layout_done);


        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageBack.setOnClickListener(this);
        layoutDone.setOnClickListener(this);


    }

    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getAddingCardJson(String userId, String card_no, String card_company,String expiry_month,String expiry_year, String stripe_customer_id) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_payment_cards");
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("card_no", card_no);
            jsonObject.accumulate("card_company", card_company);
            jsonObject.accumulate("expiry_month", expiry_month);
            jsonObject.accumulate("expiry_year", expiry_year);
            jsonObject.accumulate("stripe_customer_id", stripe_customer_id);


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
        if (v == imageBack) {
            finish();
        }
        if (v == layoutDone) {

            if (Utility.isEmptyString(editCardHolder.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.fill_holder_name), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editCardNumber.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.fill_card_no), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editExpiryMonth.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.fill_expiry_month), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editExpiryYear.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.fill_expiry_year), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editCVV.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.fill_cvv), Toast.LENGTH_SHORT);
                return;
            }

            Stripe stripe = MyApplication.getStripe();
//            Card.Builder builder = new Card.Builder(editCardNumber.getText().toString().trim()
//                    , Integer.parseInt(editExpiryMonth.getText().toString())
//                    , Integer.parseInt(editExpiryYear.getText().toString())
//                    , editCVV.getText().toString());
//            builder.name(editCardHolder.getText().toString());
//            final Card card = builder.build();
//
//            if (!card.validateCard()) {
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_card), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            if (!card.validateCVC()) {
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_cvc), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            if (!card.validateExpiryDate()) {
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_expiry_date), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            if (!card.validateExpMonth()) {
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_expiry_month), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            if (txtDone.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(this, R.string.done))) {
//
//                txtDone.setVisibility(View.GONE);
//                progressBar.setVisibility(View.VISIBLE);
//
//            }
//
//            Utility.Logger(TAG, "Card Brand = " + card.getBrand());
//            stripe.createToken(card, new TokenCallback() {
//                @Override
//                public void onError(@NonNull Exception error) {
//                    txtDone.setText(Utility.getStringFromRes(getApplicationContext(), R.string.try_again));
//                    Utility.Toaster(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
//                }

//                @Override
//                public void onSuccess(@NonNull final Token token) {
//                    Utility.Logger(TAG, "Token = " + token.getId());
//
//                    //TODO User ID
//                    management.sendRequestToServer(new RequestObject()
//                            .setJson(getAddingCardJson(/*prefObject.getUserId()*/"1", editCardNumber.getText().toString().trim(), card.getBrand()
//                                    ,editExpiryMonth.getText().toString(),editExpiryYear.getText().toString(), token.getId()))
//                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
//                            .setConnection(Constant.CONNECTION.ADD_CARD)
//                            .setConnectionCallback(new ConnectionCallback() {
//                                @Override
//                                public void onSuccess(Object data, RequestObject requestObject) {
//                                    if (data!=null) {
//
//                                        DataObject dataObject = (DataObject) data;
//                                        DataObject cardObject = null;
//                                        for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
//                                             cardObject = dataObject.getObjectArrayList().get(i);
//
//                                        }
//
//                                        txtDone.setVisibility(View.VISIBLE);
//                                        txtDone.setText(Utility.getStringFromRes(getApplicationContext(), R.string.successfully_added));
//                                        progressBar.setVisibility(View.GONE);
//
//                                        Utility.Logger(TAG,"Payment "+dataObject.getPayment_card_no()+" "+dataObject.getPayment_expiry_date()+ " "+dataObject.getPayment_id());
//
//                                        Intent intent = new Intent();
//                                        intent.putExtra(Constant.IntentKey.CARD_INFORMATION,cardObject);
//                                        setResult(RESULT_OK,intent);
//                                        finish();
//
//
//                                    }
//                                }
//
//                                @Override
//                                public void onError(String data, RequestObject requestObject) {
//
//                                    txtDone.setVisibility(View.VISIBLE);
//                                    txtDone.setText(Utility.getStringFromRes(getApplicationContext(), R.string.try_again));
//                                    progressBar.setVisibility(View.GONE);
//
//
//                                }
//                            }));
//
//
//                }
//            });
//
        }

    }



}
