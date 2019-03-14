package org.simplesns.simplesns.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static org.simplesns.simplesns.constant.GlobalUserConstant.JWT;
import static org.simplesns.simplesns.constant.GlobalUserConstant.MY_ID;
import static org.simplesns.simplesns.constant.GlobalUserConstant.MY_PASSWORD;

public class SharedPreferenceUtil {
    public static final String APP_SHARED_PREFS = "org.simpleSNS.SharedPreference";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void setSharedJWT(String jwt) {
        editor.putString(JWT, jwt);
        editor.commit();
    }

    public String getSharedJWT() {
        return sharedPreferences.getString(JWT, null);
    }

    public void setSharedMyId(String myId) {
        editor.putString(MY_ID, myId);
        editor.commit();
    }

    public String getSharedMyId() {
        return sharedPreferences.getString(MY_ID, null);
    }

    public void setSharedPassword(String password) {
        editor.putString(MY_PASSWORD, password);
        editor.commit();
    }

    public String getSharedPassword() {
        return sharedPreferences.getString(MY_PASSWORD, null);
    }
}
