package sageone.abacus.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sageone.abacus.Exceptions.FormatException;
import sageone.abacus.Helper.FormatHelper;
import sageone.abacus.Models.Data;
import sageone.abacus.R;

/**
 * Created by otomaske on 11.02.2016.
 */
public class ResultEmployeeFragment extends Fragment
{
    public static ResultEmployeeFragment instance;
    private Activity activity;

    TextView txtTitle;
    TextView txtWageGross;
    TextView txtWageNet;

    TextView txtTax;
    TextView txtWageTax;
    TextView txtSolidarity;
    TextView txtChurchTax;

    TextView txtSocial;
    TextView txtPension;
    TextView txtUnemployment;
    TextView txtCare;
    TextView txtHealth;


    public ResultEmployeeFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_result_employee, container, false);

        // get the calculation result from the activity
        Data data = activity.getIntent().getExtras().getParcelable("Data");

        _initViews(view);
        _setViewData(data);
        _initializeListener(view);

        return view;
    }


    /**
     * Instantiates a ResultEmployeeFragment.
     *
     * @param args
     * @return
     */
    public static ResultEmployeeFragment getInstance(Bundle args)
    {
        if (null == instance) {
            instance = new ResultEmployeeFragment();
        }
        instance.setArguments(args);

        return instance;
    }


    /**
     * Callback to get the visibility
     * status of this fragment.
     *
     * @param v
     */
    public void setUserVisibleHint(boolean v)
    {
        super.setUserVisibleHint(v);
        if (v) {
            instance.getActivity().setTitle(getResources().getString(R.string.result_employee_title));
        }
    }


    /**
     * Init all view activity listeners.
     *
     * @param view
     */
    private void _initializeListener(View view)
    {
        AppCompatButton btnAgain = (AppCompatButton) view.findViewById(R.id.result_employee_btn_again);
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }


    /**
     * Init all available views.
     *
     * @param view
     */
    private void _initViews(View view)
    {
        // Title views
        txtTitle = (TextView) view.findViewById(R.id.result_employee_title_wage);
        txtWageGross = (TextView) view.findViewById(R.id.result_employee_wage_gross);

        // Data views
        txtTax = (TextView) view.findViewById(R.id.result_employee_tax);
        txtWageTax = (TextView) view.findViewById(R.id.result_employee_wage_wagetax);
        txtSolidarity = (TextView) view.findViewById(R.id.result_employee_solidarity);
        txtChurchTax = (TextView) view.findViewById(R.id.result_employee_churchtax);

        txtSocial = (TextView) view.findViewById(R.id.result_employee_social_contribution);
        txtPension = (TextView) view.findViewById(R.id.result_employee_insurance_pension);
        txtUnemployment = (TextView) view.findViewById(R.id.result_employee_insurance_unemployment);
        txtCare = (TextView) view.findViewById(R.id.result_employee_insurance_care);
        txtHealth = (TextView) view.findViewById(R.id.result_employee_insurance_health);

        txtWageNet = (TextView) view.findViewById(R.id.result_employee_wage_net);
    }


    /**
     * Set all view data.
     *
     * @param data
     */
    private void _setViewData(Data data)
    {
        txtTitle.setText(_formatCurrency("1"));
        txtWageGross.setText(_formatCurrency("1"));
        txtWageNet.setText(_formatCurrency("1"));
        txtTax.setText(_formatCurrency("1"));
        txtWageTax.setText(_formatCurrency("1"));
        txtSolidarity.setText(_formatCurrency("1"));
        txtChurchTax.setText(_formatCurrency("1"));
        txtSocial.setText(_formatCurrency("1"));
        txtPension.setText(_formatCurrency("1"));
        txtUnemployment.setText(_formatCurrency("1"));
        txtCare.setText(_formatCurrency("1"));
        txtHealth.setText(_formatCurrency("1"));
    }


    private String _formatCurrency(String text)
    {
        try {
            return FormatHelper.currency(text.toString());
        } catch (FormatException e) {
            Log.e("FormatHelperError", "");
        }

        return "FormatError";
    }

}