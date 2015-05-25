/**
 * FuelStationActivity.java
 * 
 * (c) Robert Lange 2015
 * Alle Rechte beim Autor.
 */
package com.robertlange.sparesprit;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Robert Lange
 * 
 */
public class SettingsActivity extends Activity  {

    AlertDialog alertdlg;
    AutoCompleteTextView townSet;
    public static Spinner fuelType, circle, sortBy;
    public static boolean relevantChange;
    ArrayAdapter<String> adapter;
    String[] townSuggest = {"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};

    @Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //userTown = (EditText) findViewById(R.id.townSet);
        //userTown.setText("Aktuelle Posi", TextView.BufferType.EDITABLE);

        fuelType = (Spinner) findViewById(R.id.fuelSet);
        List<String> fuelList = new ArrayList<String>();
        fuelList.add("Super E5");
        fuelList.add("Super E10");
        fuelList.add("Diesel");
        ArrayAdapter<String> fuelDataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fuelList);
        fuelDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelType.setAdapter(fuelDataAdapter1);
        fuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                relevantChange = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        sortBy = (Spinner) findViewById(R.id.sortBySet);
        List<String> sortByList = new ArrayList<String>();
        sortByList.add("Preis");
        sortByList.add("Kilometer");
        sortByList.add("Kombiniert");
        ArrayAdapter<String> sortByDataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sortByList);
        sortByDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy.setAdapter(sortByDataAdapter1);

        circle = (Spinner) findViewById(R.id.circleSet);
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
        circle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                relevantChange = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, townSuggest);
        //Getting the instance of AutoCompleteTextView
        townSet = (AutoCompleteTextView)findViewById(R.id.townSetAutoComplete);
        townSet.setThreshold(1);//will start working from first character
        townSet.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        townSet.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 2) {
                    AutoCompleteServiceAccessTask task = new AutoCompleteServiceAccessTask();
                    task.execute(new String[]{""});
                }
                relevantChange = true;
            }
        });

        relevantChange = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (relevantChange) {
            relevantChange = false;
            return;
        }
    }

    private class AutoCompleteServiceAccessTask extends AsyncTask<String, Void, String> {

        private final AndrestClient rest = new AndrestClient();

        private static final String GetAutocompleteUri =
                "http://www.sparesprit.de/getautocompleteJson.php?term=";
        private List<String> townList = new ArrayList<String>();

        @Override
        protected String doInBackground(String... urls) {
            try{
                townList = GetTownListByAutocomplete();
            }
            catch(Exception e){
                String x = "t";
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("ok"))
            {
                String[] townSuggest = townList.toArray(new String[townList.size()]);
                adapter = new ArrayAdapter<String>
                        (SettingsActivity.this,android.R.layout.select_dialog_item, townSuggest);
                townSet = (AutoCompleteTextView)findViewById(R.id.townSetAutoComplete);
                townSet.setThreshold(3);
                townSet.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        private List<String> GetTownListByAutocomplete() throws RESTException, JSONException {
            List<String> retval = new ArrayList<String>();
            String requestString = GetAutocompleteUri + SettingsActivity.this.townSet.getText();
            JSONObject json = rest.request(
                    requestString,
                    "GET",
                    null
            );
            JSONArray jsonTownList = json.getJSONArray("values");
            if(jsonTownList != null) {
                for (int h = 0; h < jsonTownList.length(); h++) {
                    JSONObject row = jsonTownList.getJSONObject(h);
                    String value = row.getString("value");
                    retval.add(value);
                }
            }
            return  retval;
        }
    }
}