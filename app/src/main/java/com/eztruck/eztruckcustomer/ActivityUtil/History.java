package com.eztruck.eztruckcustomer.ActivityUtil;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eztruck.eztruckcustomer.AdapterUtil.CategoriesTabPager;
import com.eztruck.eztruckcustomer.FragmentUtil.ListOfOrder;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.PagerTabObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.util.ArrayList;

public class History extends AppCompatActivity implements View.OnClickListener {
    private String TAG = History.class.getName();
    private Management management;
    private PrefObject prefObject;
    private ArrayList<PagerTabObject> objectArrayList = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout layoutTab;
    private CategoriesTabPager featurePager;
    private TextView txtMenu;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUI(); //Initialize UI

    }



    /**
     * <p> It is used to retrieve
     * Intent Data</p>
     */
    private void initUI() {


        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.ride_history));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);


        Utility.Logger(TAG, "Working");
        management = new Management(getApplicationContext());


        //Adding Fragment into Pager Adapter

        objectArrayList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(this, R.string.schedule_order))
                .setFragment(ListOfOrder.getOrderInstance("schedule")));

        objectArrayList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(this, R.string.my_order_history))
                .setFragment(ListOfOrder.getOrderInstance("history")));

        //Initialize Viewpager

        viewPager = findViewById(R.id.view_pager_history);
        featurePager = new CategoriesTabPager(getSupportFragmentManager(), objectArrayList);
        viewPager.setAdapter(featurePager);

        //Setup Tab Layout with Pager Fragment

        layoutTab = findViewById(R.id.layout_tab);
        layoutTab.setupWithViewPager(viewPager);

        for (int i = 0; i < layoutTab.getTabCount(); i++) {

            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item_layout, null);
            //tv.setTextColor(Utility.getColourFromRes(this, R.color.));
            tv.setText(objectArrayList.get(i).getTitle());
            layoutTab.getTabAt(i).setCustomView(tv);

        }

        imageBack.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v==imageBack){
            finish();
        }
    }
}
