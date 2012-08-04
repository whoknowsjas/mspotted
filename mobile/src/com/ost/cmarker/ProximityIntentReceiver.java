package com.ost.cmarker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Proximity Alert Notifier
 *
 * @author jworkman
 */
public class ProximityIntentReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1000;
    public static String EVENT_ID_INTENT_EXTRA = "EventIDIntentExtraKey";

    /**
     * On Receive intent action
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
//        String key = LocationManager.KEY_PROXIMITY_ENTERING;
//        Boolean entering = intent.getBooleanExtra(key, false);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, null, 0);
        Notification notification = createNotification();
        notification.setLatestEventInfo(context, "Proximity Alert!", "You are near your point of interest. eventId: " + intent.getIntExtra(EVENT_ID_INTENT_EXTRA, -2), pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * Create the notification
     *
     * @return Notification
     */
    private Notification createNotification() {
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.when = System.currentTimeMillis();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledARGB = Color.WHITE;
        notification.ledOnMS = 1500;
        notification.ledOffMS = 1500;

        return notification;
    }
}
