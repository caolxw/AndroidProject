package hdu.com.smarthealth.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hdu.com.smarthealth.R;
import hdu.com.smarthealth.adapter.MainPagerAdapter;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment{

    TabLayout mTablayout;
    ViewPager mViewPager;
    private TabLayout.Tab tab_topic,tab_interest,tab_recommend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initViews(view);
        initEvents(view);
        return view;
    }

    private void initEvents(View view) {
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTablayout.getTabAt(1)) {
                    mViewPager.setCurrentItem(1);
                }else if(tab == mTablayout.getTabAt(2)){
                    mViewPager.setCurrentItem(2);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews(View view) {
        mTablayout = (TabLayout) view.findViewById(R.id.tl_tab_main);
        mViewPager = (ViewPager) view.findViewById(R.id.vpager_main);

        mViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager()));
        mTablayout.setupWithViewPager(mViewPager);

        //初始化tab
        tab_recommend = mTablayout.getTabAt(0); //推荐
        tab_topic = mTablayout.getTabAt(1);     //话题
        tab_interest = mTablayout.getTabAt(2);  //关注

        //初始化
        mViewPager.setCurrentItem(0);
    }
}