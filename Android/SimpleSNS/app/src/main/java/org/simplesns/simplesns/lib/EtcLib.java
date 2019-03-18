package org.simplesns.simplesns.lib;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * Created by Chanj on 17/02/2018.
 */

public class EtcLib {
    public final String TAG = EtcLib.class.getSimpleName();
    private volatile static EtcLib instance;

    public static EtcLib getInstance() {
        if (instance == null) {
            synchronized (EtcLib.class) {
                if (instance == null) {
                    instance = new EtcLib();
                }
            }
        }

        return instance;
    }

    /**
     * If the device doesn't have phone number. Get Device ID.
     * @param context
     * @return
     */
    private String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        //
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        String tmDevice = tm.getDeviceId();

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        String serial = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) serial = Build.SERIAL;

        if(tmDevice != null) return "01" + tmDevice;
        if(androidId != null) return "02" + androidId;
        if(serial != null) return "03" + serial;

        return null;
    }
}