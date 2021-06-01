package com.chs.contatos.app;

import android.content.Context;
import android.content.res.Configuration;

public class DeviceUtils {
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isLandscape(Context context) {
        return (context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE);
    }

    public static boolean hasTwoPane(Context context) {
        return (isTablet(context) && isLandscape(context));
    }
}