package com.ost.cmarker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;

import java.util.List;

/**
 * Tracks Proximity
 *
 * @author jworkman
 */
public abstract class ProximityRecorder {

    /* default intent action id */
    public static final String PROXIMITY_INTENT_ACTION = "com.ost.cmarker.action.ACTION_NAME";
    /*defines the radius alert should trigger in meters */
    private static float POINT_RADIUS = 10;
    /*defines the time an alert expires */
    private static long PROX_ALERT_EXPIRATION = -1;

    /**
     * Register new list of alert intents
     *
     * @param locations
     * @param locationManager
     * @param activity
     */
    public static void addAlerts(List<String> locations, LocationManager locationManager, Activity activity) {
        Intent intent = new Intent(PROXIMITY_INTENT_ACTION);
        for (int i = 0; i < locations.size(); i++) {
            intent.putExtra(ProximityIntentReceiver.EVENT_ID_INTENT_EXTRA, i);
            String[] geo = locations.get(i).split("~");
//            Toast.makeText(this, "Adding alert: "+localCounter, Toast.LENGTH_SHORT).show();
            locationManager.addProximityAlert(
                    Double.parseDouble(geo[0]), // the latitude of the central point of the alert region
                    Double.parseDouble(geo[1]), // the longitude of the central point of the alert region
                    POINT_RADIUS, // the radius of the central point of the alert region, in meters
                    PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration
                    PendingIntent.getBroadcast(activity, i, intent, PendingIntent.FLAG_CANCEL_CURRENT) // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
            );
        }
    }

    /**
     * Remove registered alert intent
     *
     * @param i
     * @param locationManager
     * @param activity
     */
    public static void removeAlert(int i, LocationManager locationManager, Activity activity) {
        Intent intent = new Intent(PROXIMITY_INTENT_ACTION);
        intent.putExtra(ProximityIntentReceiver.EVENT_ID_INTENT_EXTRA, i);
        locationManager.removeProximityAlert(PendingIntent.getBroadcast(activity, i, intent, PendingIntent.FLAG_CANCEL_CURRENT));
    }
}
