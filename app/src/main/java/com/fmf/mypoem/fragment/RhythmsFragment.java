package com.fmf.mypoem.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fmf.mypoem.fragment.dummy.DummyRhythms;
import com.fmf.mypoem.util.PoemLog;

public class RhythmsFragment extends ListFragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RhythmsFragment() {
        PoemLog.i("RhythmsFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content
        setListAdapter(new ArrayAdapter<DummyRhythms.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyRhythms.ITEMS));
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
