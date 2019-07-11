package uz.mold.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.Collection;
import java.util.Date;

import uz.mold.dialog.alert.AlertDialogBuilder;
import uz.mold.dialog.bottom_sheet.BottomSheetDialog;
import uz.mold.dialog.picker.MyDatePickerDialog;
import uz.mold.dialog.picker.MyTimePickerDialog;
import uz.mold.dialog.popup.PopupBuilder;

public class Dialog {

    //----------------------------------------------------------------------------------------------

    public static void makeDatePicker(EditText et) {
        makeDatePicker(et, false);
    }

    public static void makeDatePicker(EditText et, long startDateTime, long endDateTime) {
        makeDatePicker(et, startDateTime, endDateTime, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void makeDatePicker(final EditText et, final boolean withClearButton) {
        makeDatePicker(et, -1, -1, withClearButton);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void makeDatePicker(final EditText et, final long startDateTime, final long endDateTime, final boolean withClearButton) {
        et.setOnLongClickListener(null);
        et.setKeyListener(null);
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MyDatePickerDialog.show(et, startDateTime, endDateTime, withClearButton);
                }
                return false;
            }
        });
    }


    public static void makeDateRangePicker(
            final EditText etBegin,
            final long startDateTime,
            final EditText etEnd,
            final long endDateTime
    ) {
        makeDateRangePicker(etBegin, startDateTime, etEnd, endDateTime, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void makeDateRangePicker(
            final EditText etBegin,
            final long startDateTime,
            final EditText etEnd,
            final long endDateTime,
            final boolean withClearButton
    ) {
        etBegin.setOnLongClickListener(null);
        etBegin.setKeyListener(null);
        etBegin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Date date = DialogUtil.parse(etEnd.getText().toString());

                    long end = date != null ? date.getTime() : endDateTime;

                    MyDatePickerDialog.show(etBegin, startDateTime, end, withClearButton);
                }
                return false;
            }
        });

        etEnd.setOnLongClickListener(null);
        etEnd.setKeyListener(null);
        etEnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    Date date = DialogUtil.parse(etBegin.getText().toString());

                    long start = date != null ? date.getTime() : startDateTime;

                    MyDatePickerDialog.show(etEnd, start, endDateTime, withClearButton);
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void makeTimePicker(final EditText et) {
        et.setOnLongClickListener(null);
        et.setKeyListener(null);
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MyTimePickerDialog.show(et);
                }
                return false;
            }
        });
    }

    //----------------------------------------------------------------------------------------------

    /**
     * function makeDateAndTimePicker show Date and Time Picker dialog, Fist open
     * date picker dialog set date and time  to EditText
     *
     * @example "dd.MM.yyyy HH:mm:ss"
     */
    public static void makeDateAndTimePicker(EditText et) {
        makeDateAndTimePicker(et, -1, -1);
    }

    /**
     * function makeDateAndTimePicker show Date and Time Picker dialog, Fist open
     * date picker dialog set date and time  to EditText
     *
     * @param startDateTime set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param endDateTime   set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @example "dd.MM.yyyy HH:mm:ss"
     */
    public static void makeDateAndTimePicker(final EditText et, final long startDateTime, final long endDateTime) {
        et.setOnLongClickListener(null);
        et.setKeyListener(null);
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MyDatePickerDialog.show(et, true, startDateTime, endDateTime);
                }
                return false;
            }
        });
    }

    /**
     * function makeTimeAndDatePicker show Date and Time Picker dialog, Fist open
     * Time picker dialog set date and time  to EditText
     *
     * @example "dd.MM.yyyy HH:mm:ss"
     */
    public static void makeTimeAndDatePicker(EditText et) {
        makeTimeAndDatePicker(et, -1, -1);
    }

    /**
     * function makeTimeAndDatePicker show Date and Time Picker dialog, Fist open
     * Time picker dialog set date and time  to EditText
     *
     * @param startDateTime set Date Picker minimal  date(@param minDate  must be at millisecund),
     * @param endDateTime   set Date Picker maximal  date( @param maxDate  must be at millisecund),
     * @example "dd.MM.yyyy HH:mm:ss"
     */
    public static void makeTimeAndDatePicker(final EditText et, final long startDateTime, final long endDateTime) {
        et.setOnLongClickListener(null);
        et.setKeyListener(null);
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MyTimePickerDialog.show(et, true, startDateTime, endDateTime);
                }
                return false;
            }
        });
    }
    //----------------------------------------------------------------------------------------------

    public static BottomSheetDialog.Builder bottomSheet() {
        return new BottomSheetDialog.Builder();
    }

    //----------------------------------------------------------------------------------------------

    public static <T> void bottomSheet(FragmentActivity activity, CharSequence title, Collection<T> values, final CommandFacade<T> command) {
        bottomSheet().title(title).option(values, command).show(activity);
    }

    public static <T> void bottomSheet(FragmentActivity activity, @StringRes int resId, Collection<T> values, final CommandFacade<T> command) {
        bottomSheet().title(resId).option(values, command).show(activity);
    }

    public static <T> void bottomSheet(FragmentActivity activity, Collection<T> values, final CommandFacade<T> command) {
        bottomSheet().option(values, command).show(activity);
    }

    //----------------------------------------------------------------------------------------------

    public static <T> void bottomSheet(FragmentActivity activity, CharSequence title, T[] values, final CommandFacade<T> command) {
        bottomSheet().title(title).option(values, command).show(activity);
    }

    public static <T> void bottomSheet(FragmentActivity activity, @StringRes int resId, T[] values, final CommandFacade<T> command) {
        bottomSheet().title(resId).option(values, command).show(activity);
    }

    public static <T> void bottomSheet(FragmentActivity activity, T[] values, final CommandFacade<T> command) {
        bottomSheet().option(values, command).show(activity);
    }

    //----------------------------------------------------------------------------------------------

    /**
     * function make bottom Sheet dialod and show
     *
     * @param activity    set FragmentActivity activity must be not null
     * @param contentView set View contentView must be not null
     */
    public static void bottomSheet(FragmentActivity activity, View contentView) {
        bottomSheet().contentView(contentView).show(activity);
    }

    /**
     * function make bottom Sheet dialod and show
     *
     * @param activity    set FragmentActivity activity must be not null
     * @param contentView set View contentView must be not null
     * @param callback    set Callback callback for dialog state change actions
     */
    public static void bottomSheet(FragmentActivity activity, View contentView, Callback callback) {
        bottomSheet().contentView(contentView).callback(callback).show(activity);
    }

    //----------------------------------------------------------------------------------------------

    public static void bottomSheet(FragmentActivity activity, CharSequence title, RecyclerView.Adapter adapter) {
        bottomSheet().title(title).adapter(adapter).show(activity);
    }

    public static void bottomSheet(FragmentActivity activity, @StringRes int resId, RecyclerView.Adapter adapter) {
        bottomSheet().title(resId).adapter(adapter).show(activity);
    }

    public static void bottomSheet(FragmentActivity activity, RecyclerView.Adapter adapter) {
        bottomSheet().adapter(adapter).show(activity);
    }

    //----------------------------------------------------------------------------------------------

    public static AlertDialogBuilder alert() {
        return new AlertDialogBuilder();
    }

    //----------------------------------------------------------------------------------------------

    public static void alert(Activity activity, CharSequence title, CharSequence message) {
        alert().title(title).message(message).negative(DialogUtil.NOOP).show(activity);
    }

    public static void alert(Activity activity, @StringRes int titleResId, @StringRes int messageResId) {
        alert().title(titleResId).message(messageResId).negative(DialogUtil.NOOP).show(activity);
    }

    public static void alert(Activity activity, @StringRes int titleResId, CharSequence message) {
        alert().title(titleResId).message(message).negative(DialogUtil.NOOP).show(activity);
    }

    public static void alert(Activity activity, CharSequence title, @StringRes int messageResId) {
        alert().title(title).message(messageResId).negative(DialogUtil.NOOP).show(activity);
    }

    //----------------------------------------------------------------------------------------------

    public static void confirm(Activity activity, CharSequence title, CharSequence message, Command command) {
        alert().title(title).message(message).positive(command).negative(DialogUtil.NOOP).show(activity);
    }

    public static void confirm(Activity activity, @StringRes int titleResId, @StringRes int messageResId, Command command) {
        alert().title(titleResId).message(messageResId).positive(command).negative(DialogUtil.NOOP).show(activity);
    }

    public static void confirm(Activity activity, @StringRes int titleResId, CharSequence message, Command command) {
        alert().title(titleResId).message(message).positive(command).negative(DialogUtil.NOOP).show(activity);
    }

    public static void confirm(Activity activity, CharSequence title, @StringRes int messageResId, Command command) {
        alert().title(title).message(messageResId).positive(command).negative(DialogUtil.NOOP).show(activity);
    }

    //----------------------------------------------------------------------------------------------

    public static PopupBuilder popup() {
        return new PopupBuilder();
    }

    public static <T> void popup(View view, Collection<T> values, final CommandFacade<T> command) {
        popup().option(values, command).show(view);
    }

    public static void popup(View view, CommandPopup command) {
        popup().show(view, command);
    }

    //----------------------------------------------------------------------------------------------

}
