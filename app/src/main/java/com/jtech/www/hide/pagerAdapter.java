package com.jtech.www.hide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by others on 17-06-2017.
 */

public class pagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public pagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                video1 tab1 = new video1();
                return tab1;
            case 1:
                phot tab2 = new phot();
                return tab2;
            case 2:
                document tab3 = new document();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
