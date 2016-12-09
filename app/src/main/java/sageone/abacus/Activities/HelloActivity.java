package sageone.abacus.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import sageone.abacus.Helper.ConnectivityHandler;
import sageone.abacus.Helper.MessageHelper;
import sageone.abacus.Helper.SystemHelper;
import sageone.abacus.Helper.FileStore;
import sageone.abacus.Interfaces.ApiCallbackListener;
import sageone.abacus.Models.StationList;
import sageone.abacus.Models.Insurances;
import sageone.abacus.Models.LocationData;
import sageone.abacus.Models.TownData;
import sageone.abacus.Models.WebService;
import sageone.abacus.R;

public class HelloActivity extends AppCompatActivity implements ApiCallbackListener {

    private static final Integer CALC_TYPE_NET = 0;
    private static final Integer CALC_TYPE_GROSS = 1;

    private ConnectivityHandler connectivityHandler;
    private FileStore fileStore;
    private Dialog preparationDialog;
    private WebService webService;

    public static LocationManager lm;
    public static android.location.Location location;

    public static HelloActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        _registerConnectivityReceiver();

        Button startCalcNet = (Button) findViewById(R.id.hello_start_calculation_net);
        Button startCalcGross = (Button) findViewById(R.id.hello_start_calculation_gross);

        startCalcNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputActivity(CALC_TYPE_NET);
            }
        });

        startCalcGross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputActivity(CALC_TYPE_GROSS);
            }
        });

        TextView t = (TextView) findViewById(R.id.hello_credits);
        t.setText(t.getText().toString().replace("YYYY", Calendar.getInstance().get(Calendar.YEAR) + ""));

        instance = this;
        fileStore = new FileStore(this);

        webService = new WebService(getApplicationContext(), this);

        _prefetchLocation();
    }


    /**
     * Inflate the menu.
     * This adds items to the action bar if it is present.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Register some receiver and
     * handle connectivity changes.
     */
    private void _registerConnectivityReceiver() {
        connectivityHandler = new ConnectivityHandler(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(
                connectivityHandler,
                intentFilter
        );
    }


    /**
     * Select and display the
     * calculation input activity.
     *
     * @param type
     */
    public void showInputActivity(Integer type) {
        Intent i = new Intent(this, InputActivity.class);
        i.putExtra("calc_type", type);
        startActivity(i);
    }


    @Override
    /**
     * Main menu selection callback handling.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;
            case R.id.action_cache:
                fileStore.flush();
                _prefetchLocation();
                break;
            case R.id.action_quit:
                SystemHelper.finish(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Fetch location from API
     * and sav them to app cache.
     */
    public void _prefetchLocation() {
        try {

            lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            location = getLocationByMobile();

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(android.location.Location location) {
                    if (location.getTime() > HelloActivity.this.location.getTime() + 1000 * 20
                            && HelloActivity.this.location.distanceTo(location) > 75.0) {
                        HelloActivity.this.location = location;
                        HelloActivity.this.accessWebService();
                    } else {
                        HelloActivity.this.location = location;
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            Criteria c = new Criteria();
            c.setAccuracy(Criteria.ACCURACY_FINE);
            c.setAccuracy(Criteria.ACCURACY_COARSE);
            c.setAltitudeRequired(false);
            c.setBearingRequired(false);
            c.setCostAllowed(true);
            c.setPowerRequirement(Criteria.POWER_HIGH);
            String provider = lm.getBestProvider(c, true);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(provider, 30000, 100, locationListener);
            //Insurances i = fileStore.readInsurancesResult();
            // webService.Location("Leipzig");
            webService.Town(location.getLatitude(), location.getLongitude());
            return;
        } catch (Exception e) {

        }

        dialog(getResources().getString(R.string.preparation_dialog), true);
    }

    private android.location.Location getLocationByMobile() {
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAccuracy(Criteria.ACCURACY_COARSE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = lm.getBestProvider(c, true);
        if (location == null || (SystemClock.elapsedRealtime() - location.getTime() > 1000 * 20
                && HelloActivity.this.location.distanceTo(location) > 50.0)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            location = lm.getLastKnownLocation(provider);
        }
        // telnet localhost 5554 und geo fix 12.3833 51.3667
        if (location == null) {
            List<String> providers = lm.getAllProviders();
            location = getLastKnownLocation();
        }

        return location;
    }

    private android.location.Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        android.location.Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return null;
            }

            android.location.Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    @Override
    /**
     *  Callback for insurances api call.
     */
    public void responseFinishInsurances(Insurances i)
    {
        dismissDialog();
        fileStore.writeInsurancesResult(i);
    }

    @Override
    public void responseFailedInsurances(String message)
    {
        dismissDialog();
        dialog(message, false);
    }

    @Override
    public void responseFinishStations(StationList fuelStations) {
        String z = "test";
    }

    @Override
    public void responseFailedStations(String message) {

    }

    @Override
    public void responseFinishLocation(LocationData location) {
        LocationData l = location;
    }

    @Override
    public void responseFailedLocation(String message) {

    }

    @Override
    public void responseFinishTown(TownData town) {
        TownData t = town;
    }

    @Override
    public void responseFailedTown(String message) {

    }


    private void dialog(String message, boolean modal)
    {
        Dialog d = MessageHelper.dialog(this, modal, message);
        d.show();

        preparationDialog = d;
    }


    private void dismissDialog()
    {
        preparationDialog.dismiss();
    }

    public void accessWebService() {
        /*
        if(!isWorking) {
            isWorking = true;
            input = new Input();
            input.setTown(SettingsActivity.townSet.getText().toString());
            input.setPosCircle(SettingsActivity.circle.getSelectedItemPosition());
            input.setPosFuelType(SettingsActivity.fuelType.getSelectedItemPosition());
            input.setPosSort(SettingsActivity.sortBy.getSelectedItemPosition());
            FuelServiceAccessTask task = new FuelServiceAccessTask();
            task.execute(new String[]{""});
        }
        */
    }

}
