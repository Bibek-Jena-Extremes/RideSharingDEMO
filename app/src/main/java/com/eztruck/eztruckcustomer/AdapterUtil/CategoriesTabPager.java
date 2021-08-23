package com.eztruck.eztruckcustomer.AdapterUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.eztruck.eztruckcustomer.ObjectUtil.PagerTabObject;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CategoriesTabPager extends FragmentStatePagerAdapter {
    private ArrayList<PagerTabObject> fragmentArrayList = new ArrayList<>();

    public CategoriesTabPager(FragmentManager fm, ArrayList<PagerTabObject> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentArrayList.get(position).getTitle();
    }


}

