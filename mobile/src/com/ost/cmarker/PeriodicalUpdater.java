package com.ost.cmarker;

import android.os.Handler;
import android.os.Message;

import java.util.Calendar;

/**
 * Periodically checks for updates from the server
 *
 * @author jworkman
 */
public class PeriodicalUpdater extends Handler {
    public final static int MSG_START = 0;
    public final static int MSG_STOP = 1;
    public final static int MSG_UPDATE = 2;
    public final static int REFRESH_PERIOD = 10000; // in ms
    public final static int SPIN_PERIOD = 1000; // in ms

    private MainActivity activity;
    private long mLastTime;

    public PeriodicalUpdater(MainActivity activity) {
        super();
        this.activity = activity;
        mLastTime = 0;
    }

    /**
     * handle messages to implement the screen refresh timer
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case MSG_START:
                this.sendEmptyMessage(MSG_UPDATE);
                break;

            case MSG_UPDATE:
                this.checkTime();
                this.sendEmptyMessageDelayed(MSG_UPDATE, SPIN_PERIOD);
                break;

            case MSG_STOP:
                this.removeMessages(MSG_UPDATE);
                break;

            default:
                break;
        }
    }

    /**
     * check how much time has passed and update the UI
     */
    private void checkTime() {
        long currTime = Calendar.getInstance().getTimeInMillis();
        long dt = currTime - mLastTime;
        if (dt > REFRESH_PERIOD) {
            activity.refreshFromServer();
            mLastTime = currTime;
        }
    }
}
