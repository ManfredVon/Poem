package com.fmf.mypoem.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.adapter.RhythmListAdapter;
import com.fmf.mypoem.model.Rhythm;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmf on 15/6/11.
 */
public class RhythmsDialogFragment extends DialogFragment {

    private OnRhythmSetListener listener;
    private RhythmListAdapter adapter;

    public RhythmsDialogFragment() {
        // Required empty public constructor
        PoemLog.i(this, "constructor");
    }

    public static RhythmsDialogFragment newInstance(ArrayList<Rhythm> list) {
        RhythmsDialogFragment fragment = new RhythmsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PoemConstant.RHYTHM_LIST, list);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Rhythm> list = null;
        if (getArguments() != null) {
           list = getArguments().getParcelableArrayList(PoemConstant.RHYTHM_LIST);
        }
        adapter = new RhythmListAdapter(getActivity(), list);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root  = inflater.inflate(R.layout.fragment_rhythms_dialog, null);

        SearchView searchView = (SearchView) root.findViewById(R.id.sv_rhythm);
        searchView.setIconified(false);
//        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                PoemLog.i(RhythmsDialogFragment.this, query);

                adapter.getFilter().filter(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        TextView emptyView = (TextView) root.findViewById(R.id.tv_empty);

        ListView listView = (ListView) root.findViewById(R.id.lv_rhythm);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onRhythmSet(adapter.getData().get(position));
                getDialog().dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
//                .setTitle(R.string.tip_select_rhythm)
//                .setCustomTitle(searchView) // 键盘显示在AlertDailog下面
                .setView(root)
                .setNegativeButton("取消", null).create();
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View root  = inflater.inflate(R.layout.fragment_rhythms_dialog, container, false);

        SearchView searchView = (SearchView) root.findViewById(R.id.sv_rhythm);
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(true);
//        searchView.setQueryHint(getActivity().getString(R.string.hint_search_rhythm));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                PoemLog.i(RhythmsDialogFragment.this, query);

                adapter.getFilter().filter(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        ListView listView = (ListView) root.findViewById(R.id.lv_rhythms);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onRhythmSet(list.get(position));
                getDialog().dismiss();
            }
        });


        return root;
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnRhythmSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RhythmsDialogFragment.OnRhythmSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnRhythmSetListener {
        void onRhythmSet(Rhythm rhythm);
    }
}
