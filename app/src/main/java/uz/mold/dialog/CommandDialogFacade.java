package uz.mold.dialog;


import android.support.annotation.NonNull;

public interface CommandDialogFacade<T> {

    @NonNull
    CharSequence getName(T val);

    void apply(T val);

}

