package hdu.com.smarthealth.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hdu.com.smarthealth.fragment.InterestFragment;
import hdu.com.smarthealth.fragment.RecommendFragment;
import hdu.com.smarthealth.fragment.TopicFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = {"推荐","话题","关注"};
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 1){
            return new TopicFragment();
        }
        if (i == 2){
            return new InterestFragment();
        }
        if(i == 0){
            return new RecommendFragment();
        }
        //刚刚进入时选择 话题 Fragment
        return new RecommendFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
