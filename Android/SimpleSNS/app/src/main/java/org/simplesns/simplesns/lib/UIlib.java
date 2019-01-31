package org.simplesns.simplesns.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

public class UIlib {
    private static Context mContext;
    private static UIlib instance;

    public static UIlib getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (UIlib.class) {
                if (instance == null) {
                    instance = new UIlib();
                    mContext = context;
                }
            }
        }
        return instance;
    }

    public void setImmersiveMode() {
        int newUiOptions = 0;
        // 아이스크림 샌드위치(4.0) 이상일 경우
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        // 젤리빈(4.1) 이상일 경우
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        // 킷캣(4.4) 이상일 경우
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
