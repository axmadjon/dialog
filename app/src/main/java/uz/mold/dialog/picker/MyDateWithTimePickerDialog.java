package uz.mold.dialog.picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import uz.mold.dialog.DialogUtil;
import uz.mold.dialog.R;

public class MyDateWithTimePickerDialog {

    public final EditText et;
    private static int mYear;
    private static int mMonth;
    private static int mDay;

    private static int mHour;
    private static int mMinute;


    public MyDateWithTimePickerDialog(EditText et) {
        this.et = et;
    }

    private static Calendar parse(String s) {
        Date date = DialogUtil.parse(s);
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        return c;
    }

    public static void show(final EditText editText, boolean withClearButton) {
        show(editText, withClearButton, -1);
    }

    public static void show(final EditText editText, long minDate, long maxDate, boolean withClearButton) {
        show(editText, minDate, maxDate, withClearButton, -1);
    }

    public static void show(final EditText editText, boolean withClearButton, int thremeResId) {
        show(editText, -1, -1, withClearButton, thremeResId);
    }

    public static void show(final EditText editText, long minDate, long maxDate, final boolean withClearButton, final int thremeResId) {
        Calendar c = parse(editText.getText().toString());

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                showtime(editText, withClearButton, thremeResId);
            }
        };
        // Create a new instance of DatePickerDialog and return it
        Context context = editText.getContext();
        DatePickerDialog dpd = thremeResId == -1 ? new DatePickerDialog(context, listener, year, month, day)
                : new DatePickerDialog(context, thremeResId, listener, year, month, day);

        if (minDate != -1) {
            DatePicker picker = dpd.getDatePicker();
            picker.setMinDate(minDate);
        }

        if (maxDate != -1) {
            DatePicker picker = dpd.getDatePicker();
            picker.setMaxDate(maxDate);
        }

        if (withClearButton) {
            dpd.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.clear),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editText.setText("");
                            dialog.dismiss();
                        }
                    });
        }
        dpd.show();
    }

    public static void showtime(final EditText et, boolean withClearButton, int themeResId) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String time = et.getText().toString();
        if (time.length() > 0) {
            hour = hourOfTime(time);
            minute = minuteOfTime(time);
        }

        TimePickerDialog.OnTimeSetListener dialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                et.setText(getTime());
            }
        };
        Context ctx = et.getContext();

        TimePickerDialog tpd = themeResId == -1 ? new TimePickerDialog(ctx, dialog, hour, minute, true)
                : new TimePickerDialog(ctx, themeResId, dialog, hour, minute, true);

        if (withClearButton) {
            tpd.setButton(DialogInterface.BUTTON_NEUTRAL, et.getContext().getString(R.string.clear),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            et.setText("");
                            dialog.dismiss();
                        }
                    });
        }
        tpd.show();
    }

    private static String getTime() {
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay, mHour, mMinute);
        Date date = c.getTime();
        return DialogUtil.format(date, DialogUtil.FORMAT_AS_DATETIME);
    }


    private static int hourOfTime(String time) {
        return Integer.parseInt(time.substring(0, 2));
    }

    private static int minuteOfTime(String time) {
        return Integer.parseInt(time.substring(3));
    }
}
