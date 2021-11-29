package io.github.devstita.snapscroll;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

public class Utils {
    public static int DISPLAY_HEIGHT = 0, DISPLAY_WIDTH = 0;

    public static void updateDisplaySize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DISPLAY_HEIGHT = metrics.heightPixels;
        DISPLAY_WIDTH = metrics.widthPixels;
        Utils.debug("Display Size: " + DISPLAY_HEIGHT + ", " + DISPLAY_WIDTH);
    }

    public static void debug(String msg) {
        Log.d("Debug", msg);
    }
}
