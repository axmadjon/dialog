package uz.mold.dialog.picker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import uz.mold.dialog.DialogError;

public class MyTimePickerDialog implements TimePickerDialog.OnTimeSetListener {

    public final EditText et;

    public MyTimePickerDialog(EditText et) {
        this.et = et;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        et.setText(format(hourOfDay, minute));
    }

    public static void show(EditText et) {
        show(et, -1);
    }

    public static void show(EditText et, int themeResId) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String time = et.getText().toString();
        if (time.length() > 0) {
            hour = hourOfTime(time);
            minute = minuteOfTime(time);
        }

        MyTimePickerDialog dialog = new MyTimePickerDialog(et);
        Context ctx = et.getContext();

        TimePickerDialog tpd = themeResId == -1 ? new TimePickerDialog(ctx, dialog, hour, minute, true)
                : new TimePickerDialog(ctx, themeResId, dialog, hour, minute, true);
        tpd.show();
    }


    private static String padTime(int time) {
        if (time >= 0) {
            String r = String.valueOf(time);
            switch (r.length()) {
                case 1:
                    return "0" + r;
                case 2:
                    return r;
            }
        }
        throw DialogError.Unsupported();
    }

    private static String format(int hourOfDay, int minute) {
        return padTime(hourOfDay) + ":" + padTime(minute);
    }

    private static int hourOfTime(String time) {
        return Integer.parseInt(time.substring(0, 2));
    }

    private static int minuteOfTime(String time) {
        return Integer.parseInt(time.substring(3));
    }
}
