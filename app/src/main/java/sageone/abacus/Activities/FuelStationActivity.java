package sageone.abacus.Activities;

/**
 * FuelStationActivity.java
 *
 * (c) Robert Lange 2015
 * Alle Rechte beim Autor.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import sageone.abacus.Helper.FileStore;
import sageone.abacus.Helper.FuelStationAdapter;
import sageone.abacus.Interfaces.ApiCallbackListener;
import sageone.abacus.Models.FuelStation;
import sageone.abacus.Models.FuelStations;
import sageone.abacus.Models.InputData;
import sageone.abacus.Models.InputWrapper;
import sageone.abacus.Models.Insurances;
import sageone.abacus.Models.LocationData;
import sageone.abacus.Models.StationList;
import sageone.abacus.Models.TownData;
import sageone.abacus.Models.WebService;
import sageone.abacus.R;


/**
 * Dies ist die Listenansicht der App. Sie zeigt alle Tankstellen nach
 * Entfernung oder Preis sortiert an.
 *
 * @author Robert Lange
 *
 */
public class FuelStationActivity extends AppCompatActivity implements
        OnItemClickListener, AdapterView.OnItemSelectedListener, ApiCallbackListener {

    private FuelStationAdapter adapter;
    public LocationManager lm;
    private android.location.Location location;
    public static FuelStationActivity instance;
    AlertDialog alertdlg;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = getLocationByMobile();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String x = extras.getString("fuelType");
        }

        InputData data = new InputData();
        InputWrapper ci = new InputWrapper(data);

        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);

        WebService webService = new WebService(getApplicationContext(), this);
        webService.GetStationList(ci);

        /*
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(android.location.Location location) {
                if (location.getTime() > FuelStationActivity.this.location.getTime() + 1000 * 20
                        && FuelStationActivity.this.location.distanceTo(location) > 75.0) {
                    FuelStationActivity.this.location = location;
                    FuelStationActivity.this.accessWebService();
                } else {
                    FuelStationActivity.this.location = location;
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(provider, 30000, 100, locationListener);
        */

        alertdlg = new AlertDialog.Builder(this).create();
        refresh();
        instance = this;
    }

    public void refresh() {
        this.accessWebService();
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
                && FuelStationActivity.this.location.distanceTo(location) > 50.0)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            location = lm.getLastKnownLocation(provider);
        }
        return location;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FuelStation station = (FuelStation) adapter.getItem(position);
        String url = "http://maps.google.de/maps?saddr=" +
                location.getLatitude() + "," + location.getLongitude() + "(Startpunkt)&" +
                "daddr=" + station.getLocation().getLatitude() + "," + station.getLocation().getLongitude() +
                "(" + station.getOwner() + ")&t=h&om=0";
        // Navigation starten
        Intent viewIntent = new Intent("android.intent.action.VIEW",
                Uri.parse(url));
        startActivity(viewIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.accessWebService();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void accessWebService() {
        FuelServiceAccessTask task = new FuelServiceAccessTask();
        task.execute(new String[] { ""});
    }

    @Override
    public void responseFinishInsurances(Insurances insurances) {

    }

    @Override
    public void responseFailedInsurances(String message) {

    }

    @Override
    public void responseFinishStations(FuelStations fuelStations) {

        adapter = new FuelStationAdapter(FuelStationActivity.this, fuelStations);
        int sortPos = 0;

        //if(SettingsActivity.sortBy != null && SettingsActivity.sortBy.getSelectedItemPosition() >= 0)
        //    sortPos = SettingsActivity.sortBy.getSelectedItemPosition();

        switch(sortPos)
        {
            case 0 : adapter.sortBy(FuelStationAdapter.SortOrder.PRICE);break;
            case 1 : adapter.sortBy(FuelStationAdapter.SortOrder.DISTANCE);break;
            case 2 : adapter.sortBy(FuelStationAdapter.SortOrder.COMBINED);break;
        }

        ListView listView = (ListView) findViewById(R.id.listview); // TODO
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(FuelStationActivity.this);

        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.GONE);
    }

    @Override
    public void responseFailedStations(String message) {

    }

    @Override
    public void responseFinishLocation(LocationData location) {

    }

    @Override
    public void responseFailedLocation(String message) {

    }

    @Override
    public void responseFinishTown(TownData location) {

    }

    @Override
    public void responseFailedTown(String message) {

    }

    private class FuelServiceAccessTask extends AsyncTask<String, Void, String> {

        // private final AndrestClient rest = new AndrestClient();

        private LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);

        private static final String CoordsbytownUri =
                "http://www.sparesprit.de/api/v2/FuelApi.php?request=Coordsbytown&town=";
        private static final String TownByCoordsUri =
                "http://www.sparesprit.de/api/v2/FuelApi.php?request=townbycoords";
        private static final String DataByCoordsUri =
                "http://www.sparesprit.de/api/v2/FuelApi.php?request=databycoords";

        @Override
        protected void onPreExecute() {
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            /*
            if(SettingsActivity.relevantChange) {
                String town = SettingsActivity.townSet.getText().toString();
                int fuelPos = 0, circlePos = 0, sortPos = 0;
                try {
                    getLocationByMobile();
                    if(town == null || town.equals("")) {
                        town = GetTownByCoord(location);
                    } else {
                        location = GetCoordByTown(town);
                    }

                    /*
                    if (SettingsActivity.fuelType != null && SettingsActivity.fuelType.getSelectedItemPosition() >= 0)
                        fuelPos = SettingsActivity.fuelType.getSelectedItemPosition();

                    if (SettingsActivity.circle != null && SettingsActivity.circle.getSelectedItemPosition() >= 0)
                        circlePos = SettingsActivity.circle.getSelectedItemPosition();

                    if (SettingsActivity.sortBy != null
                            && SettingsActivity.sortBy.getSelectedItemPosition() >= 0
                            && SettingsActivity.sortBy.getSelectedItemPosition() < 2)
                        sortPos = SettingsActivity.sortBy.getSelectedItemPosition();
                    */

            /*
                    List<FuelStation> fs = GetDataByCoord(
                            location,
                            getResources().getStringArray(R.array.fuel_type_array)[fuelPos],
                            getResources().getIntArray(R.array.circle_array)[circlePos],
                            getResources().getStringArray(R.array.sort_by_array)[sortPos]
                    );
                    FuelStations.setFuelStations(fs);
                } catch (Exception e) {
                    return e.getMessage() + e.getCause() + e.getStackTrace();
                }
                return town;
            }
            */

            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            /*
            adapter = new FuelStationAdapter(FuelStationActivity.this, null);
            int sortPos = 0;

            //if(SettingsActivity.sortBy != null && SettingsActivity.sortBy.getSelectedItemPosition() >= 0)
            //    sortPos = SettingsActivity.sortBy.getSelectedItemPosition();

            switch(sortPos)
            {
                case 0 : adapter.sortBy(FuelStationAdapter.SortOrder.PRICE);break;
                case 1 : adapter.sortBy(FuelStationAdapter.SortOrder.DISTANCE);break;
                case 2 : adapter.sortBy(FuelStationAdapter.SortOrder.COMBINED);break;
            }

            ListView listView = (ListView) findViewById(R.id.listview); // TODO
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(FuelStationActivity.this);

            linlaHeaderProgress.setVisibility(View.GONE);
            */
        }

        /*
        private android.location.Location GetCoordByTown(String town) throws RESTException, JSONException, UnsupportedEncodingException {
            JSONObject jsonLoc = rest.request(CoordsbytownUri + URLEncoder.encode(town, "UTF-8"), "GET", null);

            location.reset();
            location.setLatitude( jsonLoc.getDouble("latitude") );
            location.setLongitude( jsonLoc.getDouble("longitude") );
            return location;
        }*/

        /*
        private String GetTownByCoord(android.location.Location loc) throws RESTException, JSONException {
            String requestString = TownByCoordsUri + "&latitude=" + loc.getLatitude() + "&longitude=" + loc.getLongitude();
            JSONObject jsonTown = rest.request(
                    requestString,
                    "GET",
                    null
            );
            return jsonTown.getString("town");
        }*/

        /*
        private List<FuelStation> GetDataByCoord(android.location.Location loc, String article, float distance, String sortBy) throws RESTException, JSONException {
            String homeApiRequestString = DataByCoordsUri + "&latitude=" + loc.getLatitude() + "&longitude=" + loc.getLongitude()
                    + "&article=" + article + "&distance=" + distance + "&sortBy=" + sortBy ;
            JSONObject json = rest.request(
                    homeApiRequestString,
                    "GET",
                    null
            );

            JSONArray jsonStationList = json.getJSONArray("stationList");
            List<FuelStation> psList = new ArrayList<FuelStation>();
            List<Location> psLocList = new ArrayList<Location>();

            for (int h = 0; h < jsonStationList.length(); h++) {
                JSONObject row = jsonStationList.getJSONObject(h);
                JSONArray keys = row.names();
                JSONArray values = row.toJSONArray(keys);
                FuelStation ps = new FuelStation();
                for(int i = 0 ; i < values.length(); i++){
                    if(keys.getString(i).equals("id")){
                        ps.setId(Integer.parseInt(values.getString(i)));
                    }
                    else if(keys.getString(i).equals("owner")){
                        ps.setOwner(FuelStations.matchOwner(values.getString(i)));
                    }
                    else if(keys.getString(i).equals("isOpen")){
                        ps.setIsOpen(values.getBoolean(i));
                    }
                    else if(keys.getString(i).equals("openFrom")){
                        ps.setOpenFrom(values.getString(i));
                    }
                    else if(keys.getString(i).equals("openTo")){
                        ps.setOpenTo(values.getString(i));
                    }
                    else if(keys.getString(i).equals("location")){
                        ps.setLocation(new Location(
                                        values.getJSONObject(i).getDouble("latitude"),
                                        values.getJSONObject(i).getDouble("longitude"))
                        );
                        psLocList.add(ps.getLocation());
                    }
                    else if(keys.getString(i).equals("price")){
                        ps.setPrice((float) values.getDouble(i));
                    }
                    else if(keys.getString(i).equals("address")){
                        ps.setAddress(new Address(
                                        values.getJSONObject(i).getString("street"),
                                        values.getJSONObject(i).getString("housenumber"),
                                        values.getJSONObject(i).getString("postal"),
                                        values.getJSONObject(i).getString("place"))
                        );
                    }
                    else if(keys.getString(i).equals("reporttime")){
                        ps.setReporttime(values.getString(i));
                    }
                    else if(keys.getString(i).equals("distance")){
                        ps.setDistance((float) values.getDouble(i));
                    }
                }
                psList.add(ps);
            }

            if(psList.size() > 0) {
                String googleApiRequestString = "http://maps.googleapis.com/maps/api/distancematrix/json?";
                googleApiRequestString += "origins=" + loc.getLatitude() + "," + loc.getLongitude();
                googleApiRequestString += "&destinations=";
                for(Location l : psLocList) {
                    googleApiRequestString += l.getLatitude() + "," + l.getLongitude() + "%7C";
                }
                JSONObject jsonGoogle = rest.request(
                        googleApiRequestString,
                        "GET",
                        null
                );
                JSONArray distanceList1 = jsonGoogle.getJSONArray("rows").getJSONObject(0).getJSONArray("elements");
                for (int h = 0; h < distanceList1.length(); h++) {
                    JSONObject row = distanceList1.getJSONObject(h);
                    JSONArray keys = row.names();
                    JSONArray values = row.toJSONArray(keys);
                    for (int i = 0; i < values.length(); i++) {
                        if (keys.getString(i).equals("distance")) {

                            int x = values.getJSONObject(i).getInt("value");
                            psList.get(h).setDistance(x*1.0f / 1000);
                        }
                    }
                }
            }

            return psList;
        }
        */
    }
}