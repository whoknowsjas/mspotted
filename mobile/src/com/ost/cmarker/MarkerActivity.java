package com.ost.cmarker;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Mark location activity
 *
 * @author jworkman
 */
public class MarkerActivity extends Activity {

    private LocationManager locationManager;

    /**
     * Create UI event
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Button bSave = (Button) findViewById(R.id.save_location);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (null == location)
                    Toast.makeText(MarkerActivity.this, "GPS turned off or not working", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        RemoteService.postToServer(location.getLatitude(), location.getLongitude());
                    } catch (Exception e) {
                        Toast.makeText(MarkerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
