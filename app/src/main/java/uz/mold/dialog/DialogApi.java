package uz.mold.dialog;

import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;

public class DialogApi {

    private static Application mStaticApplication;

    public static void init(Application application) {
        if (mStaticApplication != null) {
            throw new DialogError("DialogApi is init");
        }
        mStaticApplication = application;
    }

    public static Context getContext() {
        if (mStaticApplication == null) {
            throw new DialogError("DialogApi not init");
        }
        return mStaticApplication;
    }

    public static CharSequence getString(@StringRes int resId) {
        return getContext().getString(resId);
    }
}
