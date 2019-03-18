package org.simplesns.simplesns.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import android.view.View;

public class UiLib {
    private static Context mContext;
    private static UiLib instance;
    int newUiOptions = 0;


    public static UiLib getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (UiLib.class) {
                if (instance == null) {
                    instance = new UiLib();
                    mContext = context;
                }
            }
        }
        return instance;
    }

    public void setHideNavigation(boolean flag) {
        // 아이스크림 샌드위치(4.0) 이상일 경우
        if (Build.VERSION.SDK_INT >= 14 && flag) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
    }

    public void setFullScreen(boolean flag){
        // 젤리빈(4.1) 이상일 경우
        if (Build.VERSION.SDK_INT >= 16 && flag) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
    }

    public void setImmerseiveSticky(){
        // 킷캣(4.4) 이상일 경우
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // finally change the color
            ((Activity)mContext).getWindow().setStatusBarColor(color);
        }
    }

    public void apply(){
        ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
