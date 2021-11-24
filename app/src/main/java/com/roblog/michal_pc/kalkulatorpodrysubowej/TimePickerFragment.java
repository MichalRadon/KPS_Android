package com.roblog.michal_pc.kalkulatorpodrysubowej;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    public static final int FLAG_START_TIME = 0;
    public static final int FLAG_END_TIME = 1;
    public int flag_time = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void setFlagtime(int i) {
        flag_time = i;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (flag_time == FLAG_START_TIME) {

            Button timeStartBtn = (Button) getActivity().findViewById(R.id.setStartTimeBtn);
            timeStartBtn.setText(String.format("%02d:%02d", hourOfDay, minute));
        } else if (flag_time == FLAG_END_TIME) {

            Button timeEndBtn = (Button) getActivity().findViewById(R.id.setEndTimeBtn);
            timeEndBtn.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    }
}
