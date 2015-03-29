/**
 * FuelStationActivity.java
 * 
 * (c) Robert Lange 2015
 * Alle Rechte beim Autor.
 */
package com.robertlange.sparesprit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import org.json.*;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Dies ist die Listenansicht der App. Sie zeigt alle Tankstellen nach
 * Entfernung oder Preis sortiert an.
 * 
 * @author Robert Lange
 * 
 */
public class FuelStationActivity extends Activity implements
		OnItemClickListener, AdapterView.OnItemSelectedListener {

	private FuelStationAdapter adapter;
    public LocationManager lm;
    private android.location.Location location;
    private View viewContainer;
    private TextView nameAndDistance, price;
    private EditText userAddress;
    AlertDialog alertdlg;
    private Spinner fuelType, circle, sortBy;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = getLocationByMobile();

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(android.location.Location location) {
                if(location.getTime() > 1000 * 20 + FuelStationActivity.this.location.getTime())
                {
                    FuelStationActivity.this.location = location;
                    FuelStationActivity.this.accessWebService();
                }
                else {
                    FuelStationActivity.this.location = location;
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        alertdlg = new AlertDialog.Builder(this).create();
        this.accessWebService();

        //setContentView(R.layout.inputs);
        //EditText et = (EditText) findViewById(R.id.address);
        //et.setText("Test", TextView.BufferType.EDITABLE);

        fuelType = (Spinner) findViewById(R.id.fuel);
        List<String> fuelList = new ArrayList<String>();
        fuelList.add("Super E5");
        fuelList.add("Super E10");
        fuelList.add("Diesel");
        ArrayAdapter<String> fuelDataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fuelList);
        fuelDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelType.setAdapter(fuelDataAdapter1);
        fuelType.setOnItemSelectedListener(this);

        sortBy = (Spinner) findViewById(R.id.sortBy);
        List<String> sortByList = new ArrayList<String>();
        sortByList.add("Preis");
        sortByList.add("Kilometer");
        sortByList.add("Kombiniert");
        ArrayAdapter<String> sortByDataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sortByList);
        sortByDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy.setAdapter(sortByDataAdapter1);
        sortBy.setOnItemSelectedListener(this);

        circle = (Spinner) findViewById(R.id.circle);
        List<Integer> circleList = new ArrayList<Integer>();
        circleList.add(5);
        circleList.add(10);
        circleList.add(15);
        circleList.add(20);
        circleList.add(25);
        circleList.add(40);
        ArrayAdapter<Integer> circleDataAdapter1 = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, circleList);
        circleDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        circle.setAdapter(circleDataAdapter1);
        circle.setOnItemSelectedListener(this);
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
        android.location.Location location = lm.getLastKnownLocation(provider);
        if(location == null || (SystemClock.elapsedRealtime() - location.getTime()) > 1000 * 20) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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
        SoapAccessTask task = new SoapAccessTask();
        task.execute(new String[] { ""});
    }

    //starting asynchronus task
    private class SoapAccessTask extends AsyncTask<String, Void, String> {

        private final AndrestClient rest = new AndrestClient();

        // CAST THE LINEARLAYOUT HOLDING THE MAIN PROGRESS (SPINNER)
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
            String town = "";
            try{
                getLocationByMobile();
                town = GetTownByCoord(location);
                List<FuelStation> fs = GetDataByCoord(
                        location,
                        getResources().getStringArray(R.array.fuel_type_array)[FuelStationActivity.this.fuelType.getSelectedItemPosition()],
                        getResources().getIntArray(R.array.circle_array)[FuelStationActivity.this.circle.getSelectedItemPosition()],
                        getResources().getStringArray(R.array.sort_by_array)[FuelStationActivity.this.sortBy.getSelectedItemPosition()]
                );
                FuelStations.setFuelStations(fs);
            }
            catch(Exception e){
                return e.getMessage() + e.getCause() + e.getStackTrace();
            }
            return town;
        }

        @Override
        protected void onPostExecute(String result) {
            //EditText town = (EditText) findViewById(R.id.userTown);
            //town.setText(result, EditText.BufferType.EDITABLE);
            //town.clearFocus();

            adapter = new FuelStationAdapter(FuelStationActivity.this);

            switch(FuelStationActivity.this.sortBy.getSelectedItemPosition())
            {
                case 0 : adapter.sortBy(FuelStationAdapter.SortOrder.PRICE);break;
                case 1 : adapter.sortBy(FuelStationAdapter.SortOrder.DISTANCE);break;
                case 2 : adapter.sortBy(FuelStationAdapter.SortOrder.COMBINED);break;
            }

            ListView listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(FuelStationActivity.this);

            // HIDE THE SPINNER AFTER LOADING FEEDS
            linlaHeaderProgress.setVisibility(View.GONE);
        }

        private Location GetCoordByTown(String town) throws RESTException, JSONException {
            JSONObject jsonLoc = rest.request(CoordsbytownUri + town, "GET", null);
            return new Location(
                    jsonLoc.getDouble("latitude"),
                    jsonLoc.getDouble("longitude")
            );
        }

        private String GetTownByCoord(android.location.Location loc) throws RESTException, JSONException {
            String requestString = TownByCoordsUri + "&latitude=" + loc.getLatitude() + "&longitude=" + loc.getLongitude();
            JSONObject jsonTown = rest.request(
                    requestString,
                    "GET",
                    null
            );
            return jsonTown.getString("town");
        }

        private List<FuelStation> GetDataByCoord(android.location.Location loc, String article, float distance, String sortBy) throws RESTException, JSONException {
            String requestString = DataByCoordsUri + "&latitude=" + loc.getLatitude() + "&longitude=" + loc.getLongitude()
                    + "&article=" + article + "&distance=" + distance + "&sortBy=" + sortBy ;
            JSONObject json = rest.request(
                    requestString,
                    "GET",
                    null
            );

            JSONArray jsonStationList = json.getJSONArray("stationList");
            List<FuelStation> psList = new ArrayList<FuelStation>();

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
            return psList;
        }
    }
}