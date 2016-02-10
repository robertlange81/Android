package sageone.abacus.Activities;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import sageone.abacus.Helper.EventHandler;
import sageone.abacus.Exceptions.StatusCodeException;
import sageone.abacus.Exceptions.WebServiceFailureException;
import sageone.abacus.Helper.MessageHelper;
import sageone.abacus.Interfaces.ApiCallbackListener;
import sageone.abacus.Models.CalculationInputData;
import sageone.abacus.Models.Insurances;
import sageone.abacus.R;
import sageone.abacus.WebService;

/**
 *
 * @author Oliver Tomaske
 * @date 2016-01-25
 *
 */
public class InputActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, ApiCallbackListener {

    public static RadioGroup            calcType;
    public static TextView              wage;
    public static SwitchCompat          wagePeriod;
    public static RadioGroup            taxclass;
    public static Spinner               state;
    public static SeekBar               children;
    public static Button                calculate;
    public static AutoCompleteTextView  insuranceAc;
    public static SwitchCompat          churchTax;

    private Long selectedInsuranceId;
    private Double selectedWage;
    private String selectedWageType = CalculationInputData.WAGE_TYPE_NET;
    private String selectedWagePeriod = CalculationInputData.WAGE_PERIOD_MONTH;
    private Boolean selectedChurchTax = false;
    private String selectedTaxClass = "I";
    private String selectedState;
    private Double selectedChildAmount = 0.0;

    private ArrayAdapter<String> insurancesAdapter;
    private String[] insurancesList = new String[] {};
    private HashMap<String, String> insurancesMap = new HashMap<String, String>();

    private NumberFormat numberFormat;
    private EventHandler eventHandler;
    private WebService webService;
    private CalculationInputData calculationInputData;

    private static final int INSURANCES_DEFAULT_SELECTION = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        eventHandler = new EventHandler(this, getApplicationContext());
        webService = WebService.getInstance(getApplicationContext(), this);
        calculationInputData = new CalculationInputData(this);

        _initializeElements();

        _prepareState();
        _prepareInsurance();

        _initializeListener();
        _initRequestedCalcType();
    }


    /**
     * Select the requested cal type
     * given by previous activity.
     */
    private void _initRequestedCalcType()
    {
        int c = getIntent().getExtras().getInt("calc_type");
        switch (c) {
            case 1:
                calcType.check(R.id.type_gross);
                break;
            default:
                calcType.check(R.id.type_net);
        }
    }


    /**
     * Initializes all view elements
     * which was set on input activity.
     */
    private void _initializeElements()
    {
        calcType    = (RadioGroup) findViewById(R.id.type);
        wage        = (TextView) findViewById(R.id.wage);
        wagePeriod  = (SwitchCompat) findViewById(R.id.wage_period);
        state       = (Spinner) findViewById(R.id.state);
        taxclass    = (RadioGroup) findViewById(R.id.taxclass);
        children    = (SeekBar) findViewById(R.id.children);
        calculate   = (Button) findViewById(R.id.calculate);
        insuranceAc = (AutoCompleteTextView) findViewById(R.id.insuranceAc);
        churchTax   = (SwitchCompat) findViewById(R.id.church);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { }


    /**
     * Initializes user input event listeners for:
     * - calculation type
     * - wage period
     * - tax class
     * - state
     * - insurance
     * - church
     * - child amount
     */
    private void _initializeListener()
    {
        // wage type
        calcType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedWageType = (R.id.type_net == checkedId)
                        ? CalculationInputData.WAGE_TYPE_NET : CalculationInputData.WAGE_TYPE_GROSS;
            }
        });

        // wage amount
        wage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String cur = wage.getText().toString();

                if (0 == cur.length() || hasFocus) {
                    return;
                }

                boolean hasComma = cur.contains(",");
                int dec = hasComma ? 100 : 1;

                Double current = Double.valueOf(cur.replaceAll("\\D", ""));
                String formatted = numberFormat.format(current / dec);

                wage.setText(formatted);
                selectedWage = current;
            }
        });

        // wage period
        wagePeriod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                eventHandler.OnSwitchWageType(isChecked);
                selectedWagePeriod = isChecked
                        ? CalculationInputData.WAGE_PERIOD_YEAR : CalculationInputData.WAGE_PERIOD_MONTH;
            }
        });

        // tax class
        taxclass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton taxClassButton = (RadioButton) findViewById(checkedId);
                selectedTaxClass = taxClassButton.getText().toString();
            }
        });

        // states
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = state.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // health insurance
        insuranceAc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t = (TextView) view;
                String value = t.getText().toString();
                String companyNumber = insurancesMap.get(value);
                selectedInsuranceId = Long.valueOf(companyNumber);
            }
        });

        // church switch
        churchTax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                eventHandler.OnSwitchChurchType(isChecked);
                selectedChurchTax = isChecked;
            }
        });

        // child amount
        final TextView childrenValue = (TextView) findViewById(R.id.children_value);
        children.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedChildAmount = Double.valueOf(progress) / 2;
                childrenValue.setText(String.valueOf(selectedChildAmount));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // calculate button
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wage.clearFocus();
                _setData();
            }
        });

    }


    /**
     * Prepares and handles the state selection.
     */
    private void _prepareState()
    {
        String[] states = getResources().getStringArray(R.array.states);

        ArrayAdapter<String> statesDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, states);
        statesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(statesDataAdapter);
    }


    /**
     * Initializes the service call.
     * After that the listener responseFinishInsurances
     * was triggered.
     */
    private void _prepareInsurance()
    {
        _initInsurancesAdapter();
        insuranceAc.setText(R.string.insurance_init_value);

        try {
            webService.Insurances();
        } catch (StatusCodeException e) {
            e.printStackTrace();
        } catch (WebServiceFailureException e) {
            e.printStackTrace();
        }
    }


    /**
     * Actualizes the view adapter.
     */
    private void _initInsurancesAdapter()
    {
        insurancesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, this.insurancesList);

        insurancesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insuranceAc.setAdapter(insurancesAdapter);
    }


    /**
     * Init the adapter with a preset.
     * @param sel
     */
    private void _initInsurancesAdapter(int sel)
    {
        _initInsurancesAdapter();
        insuranceAc.setText(this.insurancesList[sel]);
        selectedInsuranceId = Long.valueOf(insurancesMap.get(this.insurancesList[sel]));
    }


    @Override
    /**
     *  Callback for insurances api call.
     */
    public void responseFinishInsurances(Insurances i)
    {
        for (int a = 0; a < i.data.size(); a++) {
            insurancesMap.put(i.data.get(a).name, i.data.get(a).number);
        }

        // set the list for binding the array adapter
        insurancesList = insurancesMap.keySet().toArray(new String[]{});
        _initInsurancesAdapter(INSURANCES_DEFAULT_SELECTION);
    }


    /**
     * Set all relevant data for calculation.
     */
    private void _setData()
    {
        // calc type
        calculationInputData.Berechnungsart = selectedWageType;
        // wage
        calculationInputData.Brutto = selectedWage;
        // wage period
        calculationInputData.Zeitraum = selectedWagePeriod;
        // tax class
        calculationInputData.setStKl(selectedTaxClass);
        // state
        calculationInputData.setBundesland(selectedState);
        // insurance
        calculationInputData.KKBetriebsnummer = selectedInsuranceId;
        // church
        calculationInputData.Kirche = selectedChurchTax;
        // children
        calculationInputData.setKindFrei(selectedChildAmount);

        try {
            calculationInputData.validate();
            calculationInputData.format();
        } catch (Exception e) {
            _snackbar(e.getMessage());
        }

        Log.d("Berechnungsart", String.valueOf(calculationInputData.Berechnungsart));
        Log.d("Brutto", String.valueOf(calculationInputData.Brutto));
        Log.d("Zeitraum", String.valueOf(calculationInputData.Zeitraum));
        Log.d("StKl", String.valueOf(calculationInputData.StKl));
        Log.d("Bundesland", String.valueOf(calculationInputData.Bundesland));
        Log.d("KK", String.valueOf(calculationInputData.KKBetriebsnummer));
        Log.d("Kirche", String.valueOf(calculationInputData.Kirche));
        Log.d("KindFrei", String.valueOf(calculationInputData.KindFrei));
    }


    /**
     * Shows a failed validation
     * @param message
     */
    private void _snackbar(String message)
    {
        MessageHelper.snackbar(this, message);
    }

}