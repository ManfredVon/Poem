package com.fmf.mypoem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.fmf.mypoem.R;
import com.fmf.mypoem.util.PoemLog;

public class TabsFragment extends Fragment {
    private PagerAdapter adapter;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabStrip;
    private static final String[] tabTags = {"drafts", "poems", "rhythms"};
    private static CharSequence[] tabLabels;
    private static final Class<Fragment>[] tabFragments = new Class[]{DraftsFragment.class, PoemsFragment.class, RhythmsFragment.class
    };


    public TabsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);

        tabLabels = getResources().getStringArray(R.array.tab_title);

        initPagerAndTabs(rootView);

        return rootView;
    }

    private void initPagerAndTabs(View rootView) {
        adapter = new TabPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(tabTags.length); // 默认预加载一页，即一开始就有两页被创建

        tabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PoemLog.i("onPageSelected");
//                if (viewPager != null) {
//                    tabHost.setCurrentTab(position);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Class<?> cls = tabFragments[i];
//            return Fragment.instantiate(getActivity(), cls.getName(), bundle);
            return Fragment.instantiate(getActivity(), cls.getName());
        }

        @Override
        public int getCount() {
            return tabFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabLabels[position];
        }

    }

}
