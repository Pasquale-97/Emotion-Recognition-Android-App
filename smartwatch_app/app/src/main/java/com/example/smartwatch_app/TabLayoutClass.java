package com.example.smartwatch_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smartwatch_app.home_view.thisMonthData;
import com.example.smartwatch_app.home_view.thisWeekData;
import com.example.smartwatch_app.home_view.todayData;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutClass extends Fragment {

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_layout, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);

        setupWithViewPager(mViewPager);

        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

        return v;
    }

    private void setupWithViewPager(ViewPager viewPager){
        Adapter adapter = new Adapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new todayData(), "Today");
        adapter.addFragment(new thisWeekData(), "Week");
        adapter.addFragment(new thisMonthData(), "Month");
        adapter.addFragment(new HomeFragment(), "All Time");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
