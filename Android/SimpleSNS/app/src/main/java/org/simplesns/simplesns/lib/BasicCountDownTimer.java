package org.simplesns.simplesns.lib;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import org.simplesns.simplesns.R;

public class BasicCountDownTimer {
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 1000 * 60; // 1minute
    private long countDownInterval = 1000; // 1second
    private String beginString = "";
    private String timeDivider = ":";
    private String endString = "";
    private boolean timerRunning;
    private Context mContext;
    private static BasicCountDownTimer instance;

    private BasicCountDownTimer(Context context) {
        this.mContext = context;
    }

    public static BasicCountDownTimer getInstance(Context context) {
        if (instance == null) {
            synchronized (BasicCountDownTimer.class) {
                if (instance == null) {
                    instance = new BasicCountDownTimer(context);
                }
            }
        }
        return instance;
    }

    public void setCountDownTimerFormat(String beginString, String timeDivider, String endString) {
        this.beginString = beginString;
        this.timeDivider = timeDivider;
        this.endString = endString;
    }

    public void restartTimer(TextView countDownTV, Button nextBTN) {
        stopTimer();
        startTimer(countDownTV, nextBTN);
    }

    public void pauseTimer(TextView countDownTV, Button nextBTN) {
        if (this.timerRunning) {
            stopTimer();
        } else {
            startTimer(countDownTV, nextBTN);
        }
    }

    public void startTimer(TextView countDownTV, Button nextBTN) {
        countDownTV.setTextColor(Color.RED);
        nextBTN.setClickable(true);
        nextBTN.setBackgroundColor(mContext.getResources().getColor(R.color.default_blue));

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, countDownInterval) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer(countDownTV, nextBTN);
            }

            @Override
            public void onFinish() {
                // TODO:
            }
        }.start();

        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer(TextView countDownTV, Button nextBTN) {
        int minutes = (int) timeLeftInMilliseconds / (1000 * 60);
        int seconds = (int) timeLeftInMilliseconds % (1000 * 60) / 1000;

        if (minutes == 0 && seconds == 1) {
            countDownTV.setTextColor(Color.GRAY);
            countDownTV.setText("Timeout. Try again.");
            nextBTN.setClickable(false);
            nextBTN.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            return;
        }
        String timeLeftText;
        timeLeftText = beginString + minutes;
        timeLeftText += timeDivider;
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        timeLeftText += endString;
        countDownTV.setText(timeLeftText);
    }

    public String getBeginString() {
        return beginString;
    }

    public void setBeginString(String beginString) {
        this.beginString = beginString;
    }

    public long getCountDownInterval() {
        return countDownInterval;
    }

    public void setCountDownInterval(long countDownInterval) {
        this.countDownInterval = countDownInterval;
    }

    public long getTimeLeftInMilliseconds() {
        return timeLeftInMilliseconds;
    }

    public void setTimeLeftInMilliseconds(long timeLeftInMilliseconds) {
        this.timeLeftInMilliseconds = timeLeftInMilliseconds;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }
}
