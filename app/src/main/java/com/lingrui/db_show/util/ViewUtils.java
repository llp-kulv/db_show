package com.lingrui.db_show.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.lingrui.db_show.DdApplication;

/**
 * view Utils
 *
 */
public class ViewUtils {

    private static long lastClickTime = 0;

    public synchronized static boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        long timeSpace = curTime - lastClickTime;
        if (timeSpace < 400 && timeSpace > 0) {
            return true;
        }
        lastClickTime = curTime;
        return false;
    }

    public static String getString(int resId) {
        Resources res = DdApplication.getInstance().getResources();
        return res.getString(resId);
    }

    public static String[] getStringArray(int resId) {
        Resources res = DdApplication.getInstance().getResources();
        return res.getStringArray(resId);
    }

    public static String getString(int resId, Object... items) {
        Resources res = DdApplication.getInstance().getResources();
        Object[] param = new Object[items.length];

        int i = 0;
        for (Object resObj : items) {
            if (resObj instanceof Integer) {
                Integer resV = ((Integer) resObj).intValue();
                if (resV > 0x7f000000) {
                    param[i] = getString(resV);
                } else {
                    param[i] = resObj;
                }
            } else {
                param[i] = resObj;
            }

            i++;
        }
        return String.format(res.getString(resId), param);
    }

    public static int getColor(int resId) {
        return ContextCompat.getColor(DdApplication.getInstance(), resId);
    }

    public static int getResourceIDByName(String name) {
        return DdApplication.getInstance().getResources().getIdentifier(name, "drawable", DdApplication.getInstance().getPackageName());
    }

    public static int getInteger(int resId) {
        Resources res = DdApplication.getInstance().getResources();
        return res.getInteger(resId);
    }


    /**
     * 设置颜色选择器
     *
     * @param resId
     * @return
     */
    public static ColorStateList getColorStateList(int resId) {
        return ContextCompat.getColorStateList(DdApplication.getInstance(), resId);
    }

    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(DdApplication.getInstance(), resId);
    }

    public static int getDimensionPixelSize(int resId) {
        Resources res = DdApplication.getInstance().getResources();
        return res.getDimensionPixelSize(resId);
    }

    public static boolean getBoolean(int resId) {
        Resources res = DdApplication.getInstance().getResources();
        return res.getBoolean(resId);
    }

    public static float getDensity() {
        Resources res = DdApplication.getInstance().getResources();
        return res.getDisplayMetrics().density;
    }

    public static Bitmap getBitmap(int imgRes) {
        Resources res = DdApplication.getInstance().getResources();
        return BitmapFactory.decodeResource(res, imgRes);
    }
}