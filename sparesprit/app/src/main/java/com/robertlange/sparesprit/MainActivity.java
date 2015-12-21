package com.robertlange.sparesprit;

/**
 * Created by rlange on 05.04.2015.
 */
import android.app.TabActivity;
import android.content.Intent;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements LocationListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final TabHost tabHost = getTabHost();
        // erstes Tab: Einstellungen
        TabSpec ts1 = tabHost.newTabSpec("tab-1");
        ts1.setIndicator(getString(R.string.settings_header), getResources()
                .getDrawable(android.R.drawable.ic_menu_preferences));
        Intent settings = new Intent(this, SettingsActivity.class);
        ts1.setContent(settings);
        tabHost.addTab(ts1);

        // zweites Tab: Tankstellen
        TabSpec ts2 = tabHost.newTabSpec("tab-2");
        ts2.setIndicator(getString(R.string.station_header), getResources()
                .getDrawable(android.R.drawable.ic_menu_view));
        Intent results = new Intent(this, FuelStationActivity.class);
        ts2.setContent(results);
        tabHost.addTab(ts2);

        // drittes Tab: Favoriten
        TabSpec ts3 = tabHost.newTabSpec("tab-3");
        ts3.setIndicator(getString(R.string.favorits_header), getResources()
                .getDrawable(android.R.drawable.btn_star));
        Intent favs = new Intent(this, FavoritsActivity.class);
        ts3.setContent(favs);
        tabHost.addTab(ts3);

        getTabHost().setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                int i = getTabHost().getCurrentTab();
                //Log.i("@@@@@@@@ ANN CLICK TAB NUMBER", "------" + i);
                if (i == 0) {
                    //Log.i("@@@@@@@@@@ Filtersicht", "onClick tab");
                } else if (i == 1) {
                    SettingsActivity.relevantChange = true;
                    //Log.i("@@@@@@@@@@ Ergebnissicht", "onClick tab");
                    if(FuelStationActivity.instance != null)
                        FuelStationActivity.instance.refresh();
                } else if (i == 2) {
                    SettingsActivity.relevantChange = true;
                    //Log.i("@@@@@@@@@@ Favoritensicht", "onClick tab");
                    if(FavoritsActivity.instance != null)
                        FavoritsActivity.instance.refresh();

                }

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        String x = "1";
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String x = "1";
    }

    @Override
    public void onProviderEnabled(String provider) {
        String x = "1";
    }

    @Override
    public void onProviderDisabled(String provider) {
        String x = "1";
    }
}
