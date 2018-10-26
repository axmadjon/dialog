package uz.mold.dialog.picker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import uz.mold.dialog.DialogUtil;
import uz.mold.dialog.R;

public class MyDatePickerDialog implements DatePickerDialog.OnDateSetListener {

    public final EditText et;

    public MyDatePickerDialog(EditText et) {
        this.et = et;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, 0, 0);
        Date date = c.getTime();
        String result = DialogUtil.format(date, DialogUtil.FORMAT_AS_DATE);
        et.setText(result);
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

    public static void show(final EditText editText, boolean withClearButton, int thremeResId) {
        Calendar c = parse(editText.getText().toString());

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        Context context = editText.getContext();
        DatePickerDialog dpd = thremeResId == -1 ? new DatePickerDialog(context, new MyDatePickerDialog(editText), year, month, day)
                : new DatePickerDialog(context, thremeResId, new MyDatePickerDialog(editText), year, month, day);
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
}
