package com.fmf.mypoem.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.SearchView;

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
    private List<Rhythm> list;

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

        if (getArguments() != null) {
            this.list = getArguments().getParcelableArrayList(PoemConstant.RHYTHM_LIST);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RhythmListAdapter adapter = new RhythmListAdapter(getActivity(), list);

        final SearchView searchView = new SearchView(getActivity());
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getActivity().getString(R.string.hint_search_rhythm));
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

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tip_select_rhythm)
                .setCustomTitle(searchView)
                .setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Rhythm rhythm = list.get(which);
                                listener.onRhythmSet(rhythm);
                            }
                        })
                .setNegativeButton("取消", null).create();
    }

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
