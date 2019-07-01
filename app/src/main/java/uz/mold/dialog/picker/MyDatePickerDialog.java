package uz.mold.dialog.picker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import uz.mold.dialog.DialogError;
import uz.mold.dialog.DialogUtil;
import uz.mold.dialog.R;

public class MyDatePickerDialog implements DatePickerDialog.OnDateSetListener {

    public final EditText et;
    private static int mHour = 0;
    private static int mMinute = 0;
    private static int mYear;
    private static int mMonth;
    private static int mDay;

    private static boolean isTimeDialog = false;

    public MyDatePickerDialog(EditText et) {
        this.et = et;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, mHour, mMinute);
        Date date = c.getTime();
        String result = isTimeDialog ? DialogUtil.format(date, DialogUtil.FORMAT_AS_DATETIME) : DialogUtil.format(date, DialogUtil.FORMAT_AS_DATE);
        et.setText(result);
    }

    private static EditText dataSet(EditText et, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, mHour, mMinute);
        Date date = c.getTime();
        String result = DialogUtil.format(date, DialogUtil.FORMAT_AS_DATETIME);
        et.setText(result);
        return et;
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
     * set date to EditText
     *
     * @param withClearButton the when true show clear button in the dialog,
     */
    public static void show(final EditText editText, boolean withClearButton) {
        show(editText, withClearButton, -1);
    }

    /**
     * The show function helps to extract dialogs
     * set date to EditText
     *
     * @param withtimeDialog when true show Time Picker Dailog  button in the dialog,
     */
    public static void show_(final EditText editText, boolean withtimeDialog) {
        show(editText, withtimeDialog, -1, -1, false, -1);
    }

    /**
     * The show function helps to extract dialogs
     * set date to EditText
     *
     * @param minDate         set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate         set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @param withClearButton the when true show clear button in the dialog,
     */
    public static void show(final EditText editText, long minDate, long maxDate, boolean withClearButton) {
        show(editText, minDate, maxDate, withClearButton, -1);
    }

    /**
     * The show function helps to extract dialogs
     * set date to EditText
     *
     * @param withtimeDialog when true show Time Picker Dailog  button in the dialog,
     * @param minDate        set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate        set Date Picker maximal  date( @param maxDate  must be at millisecund),
     */
    public static void show(final EditText editText, boolean withtimeDialog, long minDate, long maxDate) {
        show(editText, withtimeDialog, minDate, maxDate, false, -1);
    }

    public static void show(final EditText editText, boolean withClearButton, int thremeResId) {
        show(editText, -1, -1, withClearButton, thremeResId);
    }

    /**
     * The show function helps to extract dialogs
     * set date to EditText
     *
     * @param minDate         set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate         set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @param withClearButton the when true show clear button in the dialog,
     * @param thremeResId     the resource ID of the theme against which to inflate
     *                        this dialog, or {@code 0} to use the parent
     *                        {@code context}'s default alert dialog theme
     */
    public static void show(final EditText editText, long minDate, long maxDate, boolean withClearButton, int thremeResId) {
        show(editText, false, minDate, maxDate, withClearButton, thremeResId);
    }


    /**
     * The show function helps to extract dialogs
     * set date to EditText
     *
     * @param withtimeDialog when true show Time Picker Dailog  button in the dialog,
     * @param minDate        set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate        set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @param thremeResId    the resource ID of the theme against which to inflate
     *                       this dialog, or {@code 0} to use the parent
     *                       {@code context}'s default alert dialog theme
     */
    public static void show(final EditText editText, boolean withtimeDialog, long minDate, long maxDate, int thremeResId) {
        show(editText, withtimeDialog, minDate, maxDate, false, thremeResId);
    }

    /**
     * The show function helps to extract dialogs
     * set date to EditText
     *
     * @param withtimeDialog  when true show Time Picker Dailog  button in the dialog,
     * @param minDate         set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param maxDate         set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @param withClearButton the when true show clear button in the dialog,
     * @param thremeResId     the resource ID of the theme against which to inflate
     *                        this dialog, or {@code 0} to use the parent
     *                        {@code context}'s default alert dialog theme
     */
    public static void show(final EditText editText, boolean withtimeDialog, final long minDate, final long maxDate, boolean withClearButton, int thremeResId) {
        Calendar c = parse(editText.getText().toString());

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        isTimeDialog = withtimeDialog;


        // Create a new instance of DatePickerDialog and return it
        Context context = editText.getContext();
        DatePickerDialog dpd = thremeResId == -1 ? new DatePickerDialog(context, new MyDatePickerDialog(editText), mYear, mMonth, mDay) {
            @Override
            public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
                super.onDateChanged(view, year, month, dayOfMonth);
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
            }
        }
                : new DatePickerDialog(context, thremeResId, new MyDatePickerDialog(editText), mYear, mMonth, mDay) {
            @Override
            public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
                super.onDateChanged(view, year, month, dayOfMonth);
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
            }
        };

        if (minDate != -1) {
            DatePicker picker = dpd.getDatePicker();
            picker.setMinDate(minDate);
        }

        if (maxDate != -1) {
            DatePicker picker = dpd.getDatePicker();
            picker.setMaxDate(maxDate);
        }

        if (withtimeDialog) {
            dpd.setButton(DialogInterface.BUTTON_NEUTRAL, padTime(mHour) + ":" + padTime(mMinute),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyTimePickerDialog.show(dataSet(editText, mYear, mMonth, mDay), true, false, minDate, maxDate, -1);
                            dialog.dismiss();
                        }
                    });
        } else if (withClearButton) {
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
}
