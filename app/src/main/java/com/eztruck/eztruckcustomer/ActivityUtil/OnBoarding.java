package com.eztruck.eztruckcustomer.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.eztruck.eztruckcustomer.AdapterUtil.FeaturePager;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.CustomUtil.ExtensiblePageIndicator;
import com.eztruck.eztruckcustomer.FragmentUtil.OnBoardFragment;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.OnBoardObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.util.ArrayList;


public class OnBoarding extends AppCompatActivity implements View.OnClickListener {
    private TextView txtLogin;
    private TextView txtSignUp;
    private TextView txtSkip;
    private ExtensiblePageIndicator flexibleIndicator;
    private ViewPager viewPagerBoarding;
    private FeaturePager featurePager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private Management management;
    private ImageView imageClose;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private String TAG = OnBoarding.class.getName();
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_boarding);

        initUI(); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtLogin = findViewById(R.id.txt_login);
        txtSignUp = findViewById(R.id.txt_sign_up);
        imageClose = findViewById(R.id.image_close);


        management = new Management(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Add On Boarding Data into Arraylist

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ic_onboard_taxi
                , Utility.getStringFromRes(this, R.string.title_01)
                , Utility.getStringFromRes(this, R.string.board_01))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ic_onboard_calendar
                , Utility.getStringFromRes(this, R.string.title_02)
                , Utility.getStringFromRes(this, R.string.board_02))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ic_onboard_money
                , Utility.getStringFromRes(this, R.string.title_03)
                , Utility.getStringFromRes(this, R.string.board_03))));

        //Initialize Pager & its indicator as well as connect it with its adapter

        flexibleIndicator = findViewById(R.id.flexibleIndicator);
        viewPagerBoarding = findViewById(R.id.view_pager_boarding);

        featurePager = new FeaturePager(getSupportFragmentManager(), fragmentArrayList);
        viewPagerBoarding.setAdapter(featurePager);

        flexibleIndicator.initViewPager(viewPagerBoarding);


        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        ///imageClose.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v == txtLogin) {
            startActivity(new Intent(this, Login.class));
            //finish();
        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class).putExtra(Constant.IntentKey.LOGIN_REQUIRED, true));
            //finish();
        }
        if (v == imageClose) {
            finish();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if userObject is signed in (non-null) and update UI accordingly.

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }





}




