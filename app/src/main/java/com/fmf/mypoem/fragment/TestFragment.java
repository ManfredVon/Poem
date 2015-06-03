package com.fmf.mypoem.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.poem.PoemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestFragment extends ListFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String[] items = {"Item 1", "Item 2", "Item 3"};
    private List<String> list = new ArrayList<>();
    private ArrayAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestFragment() {
        PoemLog.i("RhythmsFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list.addAll(Arrays.asList(items));

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        setListAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rhythms, null);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe);
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
        }, 5000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PoemLog.i("RhythmsFragment--onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        PoemLog.i("RhythmsFragment--onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        PoemLog.i("RhythmsFragment--onResume");
    }
}
