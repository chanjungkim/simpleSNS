package org.simplesns.simplesns;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

public class MyApp extends Application {
    // 무슨 내용을 넣어야하지?
    public static int getScreenWidth(Activity activity) {
        // Get screen dimension.
        Display display = (activity).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Log.e("Width", "" + width);

        return width;
    }
}