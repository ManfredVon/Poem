package com.fmf.poem.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

import com.fmf.poem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmf on 15/4/14.
 */
public class DemoActivity extends ListActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> list = new ArrayList<>(10);
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rhythms);


        initViews();
    }

    private void initViews() {
        final int len = 10;
        for(int i=0; i< len; i++){
            list.add("Item"+i);
        }
//        list.addAll(Arrays.asList(items));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        setListAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //        list.clear();
                final int len = 5;
                List list = new ArrayList(len);
                for (int i = 0; i < len; i++) {
                    list.add(0, "Item new");
                }

                adapter.addAll(list);
                list.clear();
                list = null;

                swipeRefreshLayout.setRefreshing(false);

            }
        }, 2000);
    }

}
