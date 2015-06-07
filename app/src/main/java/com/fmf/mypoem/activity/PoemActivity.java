package com.fmf.mypoem.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.fmf.mypoem.R;
import com.fmf.mypoem.adapter.TabPagerAdapter;
import com.fmf.mypoem.fragment.BasePoemFragment;
import com.fmf.mypoem.fragment.DraftsFragment;
import com.fmf.mypoem.fragment.PoemsFragment;
import com.fmf.mypoem.fragment.RhythmsFragment;
import com.fmf.mypoem.poem.PoemLog;
import com.fmf.mypoem.util.StringUtil;

import java.lang.reflect.Field;

public class PoemActivity extends BaseActivity {
    private PagerAdapter adapter;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabStrip;
    private CharSequence[] tabTitles;
    private String curQueryComparison;
    private int curPos;
    private BasePoemFragment curFragment;
    private BasePoemFragment[] tabFragments;
    private static final Class<Fragment>[] tabFragmentClasses = new Class[]{DraftsFragment.class, PoemsFragment.class, RhythmsFragment.class
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_poem;
    }

    @Override
    protected boolean hasToolBar() {
        return true;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);

        initPagerAndTabs();

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // 搜索
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            PoemLog.i(this, "query=" + query);

            query(query);
        }
    }

    private void query(String query) {
        if (isQuery(query)) {
            curFragment.query(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poem, menu);

        setUpSearchMenuItem(menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void setUpSearchMenuItem(Menu menu) {
        // 关联searchable.xml配置和SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // false:搜索图标不显示在编辑框内,而是显示在其前面
        searchView.setSubmitButtonEnabled(true);

        //通过反射，修改默认的样式，可以从android的search_view.xml中找到需要的组件
        try {
            Field field = searchView.getClass().getDeclaredField("mSubmitButton");
            field.setAccessible(true);
            ImageView iv = (ImageView) field.get(searchView);
            iv.setImageResource(R.drawable.ic_action_submit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 无论onClose返回true或false，点x时edittext都不会hidden
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                // to avoid click x button and the edittext hidden
//                return true;
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                // send Intent.ACTION_SEARCH
                return false;

                // this method handle it, do not send Intent.ACTION_SEARCH
                //return true;
            }

            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.length() > 0) {
//                    currentSearchTip = newText;
//                    showSearchTip(newText);
//                    PoemLog.i("Query: " + newText);
                }
                return true;
            }
        });

//        searchView.setSuggestionsAdapter();

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                PoemLog.i(PoemActivity.this, "onMenuItemActionExpand");
                // true: expand; flase: no expand
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                PoemLog.i(PoemActivity.this, "onMenuItemActionCollapse");

                query(null); // list all

                // true: collapse; flase: no collapse
                return true;
            }
        });
    }

    private boolean isQuery(@Nullable String query) {
        if (query != null) {
            query = query.trim();
        }
        String comparison = getQueryComparison(query);

        PoemLog.i(this, "comparison="+comparison);
        PoemLog.i(this, "curQueryComparison="+curQueryComparison);

        if (comparison.equals(curQueryComparison)) {
            return false;
        }

        curQueryComparison = comparison;
        return true;
    }

    private String getQueryComparison(String query) {
        return StringUtil.prefix(curPos + "#", query);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_compose:
                gotoCompose();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoCompose() {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivity(intent);
    }

    private void initPagerAndTabs() {
        tabTitles = getResources().getStringArray(R.array.tab_title);
        int len = tabFragmentClasses.length;
        tabFragments = new BasePoemFragment[len];
        for (int i = 0; i < len; i++) {
            Bundle args = new Bundle();
            args.putCharSequence(BasePoemFragment.PAGE_TITLE, tabTitles[i]);
            tabFragments[i] = (BasePoemFragment) Fragment.instantiate(this, tabFragmentClasses[i].getName(), args);
        }
        setCur(0);

        adapter = new TabPagerAdapter(getSupportFragmentManager(), tabFragments);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(len); // 默认预加载一页，即一开始就有两页被创建

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PoemLog.i(PoemActivity.this, "onPageSelected");
                setCur(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setCur(int pos) {
        curPos = pos;
        curFragment = tabFragments[pos];
    }

}
