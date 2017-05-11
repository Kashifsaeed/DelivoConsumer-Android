package com.attribe.delivo.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.PickDetailFragment;
import com.attribe.delivo.app.fragments.StepFour;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.teststeplayout.MainStepActivity;

/**
 * Author: Uzair Qureshi
 * Date:  5/3/17.
 * Description:
 */

public class PagerAdapter extends FragmentPagerAdapter
{
    private MainStepActivity activity;
    public PagerAdapter(FragmentManager fm, MainStepActivity activity) {
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
                return new PickDetailFragment();
            case 2:
                return new DropLocationFragment();
            case 3:
                return new StepFour();



        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}
