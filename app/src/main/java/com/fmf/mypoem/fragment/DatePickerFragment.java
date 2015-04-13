package com.fmf.mypoem.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    private OnDateSetListner listner;

    public DatePickerFragment() {
        // Required empty public constructor
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
//        Bundle args = getArguments();
//        int year = args.getInt(YEAR, calendar.get(Calendar.YEAR));
//        int month = args.getInt(MONTH, calendar.get(Calendar.MONTH));
//        int day = args.getInt(DAY, calendar.get(Calendar.DAY_OF_MONTH));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        listner.onDateSet(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listner = (OnDateSetListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DatePickerFragment.OnDateSetListner");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
    }

    public static interface OnDateSetListner {
        public void onDateSet(int year, int monthOfYear, int dayOfMonth);
    }
}
