package com.attribe.delivo.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.PickDetailsFragment;
import com.attribe.delivo.app.fragments.DropDetailFragment;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.screens.OrderContainerActivity;

/**
 * Author: Uzair Qureshi
 * Date:  5/3/17.
 * Description:
 */

public class PagerAdapter extends FragmentPagerAdapter
{
    private OrderContainerActivity activity;
    public PagerAdapter(FragmentManager fm, OrderContainerActivity activity) {
        super(fm);
        this.activity=activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new PickLocationFragment();
            case 1:
                return new PickDetailsFragment();
            case 2:
                return new DropLocationFragment();
            case 3:
                return new DropDetailFragment();



        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}
