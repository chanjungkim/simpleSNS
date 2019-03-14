package org.simplesns.simplesns.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.sign.FirstActivity;

public class SplashActivity extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            Intent mainIntent = new Intent(SplashActivity.this, FirstActivity.class);
            this.startActivity(mainIntent);
            this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
