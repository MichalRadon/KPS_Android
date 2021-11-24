package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;
    public int flag = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setFlag(int i) {
        flag = i;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        int monthPlus1 = view.getMonth() + 1;
        if (flag == FLAG_START_DATE) {
            Button dateStartBtn = (Button) getActivity().findViewById(R.id.setStartDateBtn);
            dateStartBtn.setText(view.getDayOfMonth() + "/" + monthPlus1 + "/" + view.getYear());
        } else if (flag == FLAG_END_DATE) {
            Button dataEndBtn = (Button) getActivity().findViewById(R.id.setEndDateBtn);
            dataEndBtn.setText(view.getDayOfMonth() + "/" + monthPlus1 + "/" + view.getYear());
        }
    }

}
