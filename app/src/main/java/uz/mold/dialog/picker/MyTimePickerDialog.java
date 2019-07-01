package uz.mold.dialog.picker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import uz.mold.dialog.DialogError;
import uz.mold.dialog.DialogUtil;
import uz.mold.dialog.R;

public class MyTimePickerDialog implements TimePickerDialog.OnTimeSetListener {

    public final EditText et;
    private static int mYear;
    private static int mMonth;
    private static int mDay;
    private static int mHour = 0;
    private static int mMinute = 0;

    private static boolean isDateDialog = false;


    public MyTimePickerDialog(EditText et) {
        this.et = et;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay, hourOfDay, minute);
        Date date = c.getTime();
        if (isDateDialog) {
            et.setText(DialogUtil.format(date, DialogUtil.FORMAT_AS_DATETIME));
        } else {
            et.setText(format(hourOfDay, minute));
        }
    }

    private static EditText timeSet(EditText et, int mHour, int mMinute) {
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay, mHour, mMinute);
        Date date = c.getTime();
        String result = DialogUtil.format(date, DialogUtil.FORMAT_AS_DATETIME);
        et.setText(result);
        return et;
    }

    /**
     * The show function helps to extract dialogs
     * set time to EditText
     */
    public static void show(EditText et) {
        show(et, -1);
    }

    /**
     * The show function helps to extract dialogs
     * set time to EditText
     *
     * @param themeResId the resource ID of the theme against which to inflate
     *                   this dialog, or {@code 0} to use the parent
     *                   {@code context}'s default alert dialog theme
     */
    public static void show(EditText et, int themeResId) {
        show(et, false, false, themeResId);
    }

    /**
     * The show function helps to extract dialogs
     * set time to EditText
     *
     * @param withDateDialog when true show Date Picker Dailog  button in the dialog,
     * @param themeResId     the resource ID of the theme against which to inflate
     *                       this dialog, or {@code 0} to use the parent
     *                       {@code context}'s default alert dialog theme
     */
    public static void show(EditText et, int themeResId, boolean withDateDialog) {
        show(et, withDateDialog, false, themeResId);
    }


    private static Calendar parse(String s) {
        Date date = DialogUtil.parse(s);
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        return c;
    }

    /**
     * The show function helps to extract dialogs
     * set time to EditText
     *
     * @param withDateDialog when true show Date Picker Dailog  button in the dialog,
     * @param minDate        set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate        set Date Picker maximal  date( @param maxDate  must be at millisecund),
     */
    public static void show(final EditText et, boolean withDateDialog, long minDate, long maxDate) {
        show(et, withDateDialog, false, minDate, maxDate, -1);
    }

    /**
     * The show function helps to extract dialogs
     * set time to EditText
     *
     * @param withDateDialog  when true show Date Picker Dailog  button in the dialog,
     * @param withClearButton the when true show clear button in the dialog,
     * @param themeResId      the resource ID of the theme against which to inflate
     *                        this dialog, or {@code 0} to use the parent
     *                        {@code context}'s default alert dialog theme
     */
    public static void show(final EditText et, boolean withDateDialog, boolean withClearButton, int themeResId) {
        show(et, withDateDialog, withClearButton, -1, -1, themeResId);
    }

    /**
     * The show function helps to extract dialogs
     * set time to EditText
     *
     * @param withDateDialog  when true show Date Picker Dailog  button in the dialog,
     * @param minDate         set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate         set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @param withClearButton the when true show clear button in the dialog,
     * @param themeResId      the resource ID of the theme against which to inflate
     *                        this dialog, or {@code 0} to use the parent
     *                        {@code context}'s default alert dialog theme
     */
    public static void show(final EditText et, boolean withDateDialog, boolean withClearButton, final long minDate, final long maxDate, int themeResId) {
        Calendar c = parse(et.getText().toString());
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        isDateDialog = withDateDialog;
        String time = et.getText().toString();

        if (withDateDialog) {
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        } else if (time.length() > 0) {
            mHour = hourOfTime(time);
            mMinute = minuteOfTime(time);
        }

        MyTimePickerDialog dialog = new MyTimePickerDialog(et);
        Context ctx = et.getContext();

        TimePickerDialog tpd = themeResId == -1 ? new TimePickerDialog(ctx, dialog, mHour, mMinute, true) {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                super.onTimeChanged(view, hourOfDay, minute);
                mDay = hourOfDay;
                mMinute = minute;
            }
        }
                : new TimePickerDialog(ctx, themeResId, dialog, mHour, mMinute, true) {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                super.onTimeChanged(view, hourOfDay, minute);
                mDay = hourOfDay;
                mMinute = minute;
            }
        };

        if (withDateDialog) {
            tpd.setButton(DialogInterface.BUTTON_NEUTRAL, DialogUtil.format(c.getTime(), DialogUtil.FORMAT_AS_DATE),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyDatePickerDialog.show(timeSet(et, mHour, mMinute), true, minDate, maxDate);
                            dialog.dismiss();
                        }
                    });

        } else if (withClearButton) {
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
