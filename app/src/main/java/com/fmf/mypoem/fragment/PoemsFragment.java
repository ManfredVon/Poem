package com.fmf.mypoem.fragment;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fmf.mypoem.fragment.dummy.DummyPoems;
import com.fmf.mypoem.util.PoemLog;

public class PoemsFragment extends ListFragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PoemsFragment() {
        PoemLog.i("PoemsFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content
        setListAdapter(new ArrayAdapter<DummyPoems.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyPoems.ITEMS));
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


}
