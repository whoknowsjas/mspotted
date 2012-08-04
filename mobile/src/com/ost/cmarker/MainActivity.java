package com.ost.cmarker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Main tabbed activity
 *
 * @author jworkman
 */
public class MainActivity extends Activity {

    private static List<String> cachedLocations;
    private PeriodicalUpdater pUpdater;
    private ProximityIntentReceiver proximityReceiver;
    private LocationManager locationManager;
    private ListView tLocations;
    private ArrayAdapter<String> tAdapter;

    /**
     * on Create UI
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        pUpdater = new PeriodicalUpdater(this);
        proximityReceiver = new ProximityIntentReceiver();
        registerReceiver(proximityReceiver, new IntentFilter(ProximityRecorder.PROXIMITY_INTENT_ACTION));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Tab 1");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("Tab 2");
        spec2.setContent(R.id.tab2);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        tabHost.setCurrentTab(0);
    }

    /**
     * on Pause UI event
     */
    @Override
    protected void onPause() {
        super.onPause();
        pUpdater.sendEmptyMessage(PeriodicalUpdater.MSG_STOP);
    }

    /**
     * on Resume UI Event
     */
    @Override
    protected void onResume() {
        super.onResume();
        pUpdater.sendEmptyMessage(PeriodicalUpdater.MSG_START);
    }

    /**
     * on Close UI
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(proximityReceiver);
        for (int i = 0; i < cachedLocations.size(); i++) {
            ProximityRecorder.removeAlert(i, locationManager, this);
            cachedLocations.remove(i);
        }
    }

    /**
     * Gets a fresh list from the server
     */
    public void refreshFromServer() {
        try {
            tLocations = (ListView) findViewById(R.id.marker_locations);
//            Toast.makeText(ListActivity.this, "Refreshing list of locations with server", Toast.LENGTH_SHORT).show();
            setCachedLocations(RemoteService.getFromServer());
            ProximityRecorder.addAlerts(getCachedLocations(), locationManager, this);
            tAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getCachedLocations());
            tLocations.setAdapter(tAdapter);
            tLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + i);
                    final int positionToRemove = i;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ProximityRecorder.removeAlert(positionToRemove, locationManager, MainActivity.this);
                            tAdapter.notifyDataSetChanged();
                        }
                    });
                    adb.show();
                }

            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets a new or returns an existing cached locations
     *
     * @return List
     */
    public static List<String> getCachedLocations() {
        if (null == cachedLocations) cachedLocations = new ArrayList<String>();
        return cachedLocations;
    }

    /**
     * Sets the cached locations
     *
     * @param cachedLocations
     */
    public static void setCachedLocations(List<String> cachedLocations) {
        MainActivity.cachedLocations = cachedLocations;
    }
}
