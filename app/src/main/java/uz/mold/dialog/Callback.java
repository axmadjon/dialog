package uz.mold.dialog;

import android.support.annotation.NonNull;
import android.view.View;

public interface Callback {
    boolean onStateChanged(@NonNull View bottomSheet, int newState);

    boolean onSlide(@NonNull View bottomSheet, float slideOffset);
}
