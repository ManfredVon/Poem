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
import android.widget.TabHost;

import com.fmf.mypoem.R;

public class TabsFragment extends Fragment {
    private PagerAdapter adapter;
    private ViewPager viewPager;
    private TabHost tabHost;
    private static final String[] tabTags = {"drafts", "poems", "rhythms"};
    private static final CharSequence[] tabLabels = {"草稿", "我的诗词", "格律"};
    private static final Class<Fragment>[] fragmentClasses = new Class[]{DraftsFragment.class, PoemsFragment.class, RhythmsFragment.class
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

        initTabs(rootView);

        initPager(rootView);

        return rootView;
    }

    private void initPager(View rootView) {
        adapter = new TabPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager != null) {
                    tabHost.setCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabs(View rootView) {
        tabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
        tabHost.setup();

        for (int i = 0; i < tabTags.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTags[i]).setIndicator(tabLabels[i])
                    .setContent(android.R.id.tabcontent);
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(tabHost.getCurrentTab());
                }
            }
        });
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Class<?> cls = fragmentClasses[i];
//            return Fragment.instantiate(getActivity(), cls.getName(), bundle);
            return Fragment.instantiate(getActivity(), cls.getName());
        }

        @Override
        public int getCount() {
            return fragmentClasses.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabLabels[position];
        }
    }

}
