package com.lingrui.db_show.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lingrui.db_show.DdApplication;

public final class ScreenUtils {
    public static int dip2px(float dpValue) {
        final float scale = DdApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = DdApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float getDensity(Context context) {
        return DdApplication.getInstance().getResources().getDisplayMetrics().density;
    }

    public static void showSoftKeyboard(Dialog context) {
        if (context == null) {
            return;
        }

        final View v = context.getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager iMethodManager = (InputMethodManager) context.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            iMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void hideSoftKeyboardIfShow(Activity context) {
        if (context == null) {
            return;
        }

        if (isImeShow(context)) {
            final View v = context.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) DdApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    public static boolean isImeShow(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
}
