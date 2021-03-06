package sageone.abacus.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sageone.abacus.Activities.ResultActivity;
import sageone.abacus.Helper.FormatHelper;
import sageone.abacus.Models.StationList;
import sageone.abacus.Helper.FileStore;
import sageone.abacus.R;

/**
 * Created by otomaske on 11.02.2016.
 */
public class ResultHomeFragment extends Fragment
{
    protected static ResultHomeFragment instance;

    private TextView wageGross;
    private TextView wageNet;

    private TextView compareWageGross;
    private TextView compareWageNet;

    private TextView wageDiffGross;
    private TextView wageDiffNet;

    public ResultHomeFragment() { }

    public static ResultHomeFragment getInstance()
    {
        if (null == instance) {
            instance = new ResultHomeFragment();
        }

        return instance;
    }


    /**
     * Instantiates a new Fragment.
     *
     * @param args
     * @return
     */
    public static ResultHomeFragment getInstance(Bundle args)
    {
        instance = getInstance();
        instance.setArguments(args);

        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = null;

        // prepare the calculation stationList
        StationList stationList = (StationList) getActivity().getIntent().getExtras().getParcelable("StationList");
        FileStore f = new FileStore(getContext());

        // try to fetch previous stationList and set compare layout if so ..
        try {
            //StationList stationListCompare = f.readStationList();
            // v = _prepareCompareLayout(inflater, stationList, stationListCompare, container);
        } catch (Exception e) {
            v = _prepareResultLayout(inflater, stationList, container);
        }

        // Cache result for comparison
        //FileStore fs = new FileStore(getActivity());
        //fs.writeStationList(stationList);

        return v;
    }


    /**
     * Prepares the result layout.
     *
     * @param inflater
     * @param stationList
     * @param container
     * @return
     */
    private View _prepareResultLayout(LayoutInflater inflater,
                                      StationList stationList, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.fragment_result_intro, container, false);
        _initListener(view);

        // result stationList
        wageGross = (TextView) view.findViewById(R.id.result_intro_wage_gross);
        wageNet = (TextView) view.findViewById(R.id.result_intro_wage_net);

        try {
            wageGross.setText(
                    FormatHelper.currency("1"));
            wageNet.setText(
                    FormatHelper.currency("1"));
        } catch (Exception e) {}

        return view;
    }


    /**
     * Prepares the compare layout.
     *
     * @param inflater
     * @param stationListResult
     * @param stationListCompare
     * @param container
     * @return
     */
    private View _prepareCompareLayout(LayoutInflater inflater, StationList stationListResult,
                                       StationList stationListCompare, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.fragment_compare_intro, container, false);
        _initListener(view);

        // result data
        wageGross = (TextView) view.findViewById(R.id.result_intro_wage_gross);
        wageNet = (TextView) view.findViewById(R.id.result_intro_wage_net);

        // compare data
        compareWageGross = (TextView) view.findViewById(R.id.compare_intro_wage_gross);
        compareWageNet = (TextView) view.findViewById(R.id.compare_intro_wage_net);


        return view;
    }


    /**
     * Initialize the on click events.
     *
     * @param view
     */
    private void _initListener(View view)
    {
        // listen on the button clicks
        AppCompatButton btnEmployee = (AppCompatButton) view.findViewById(R.id.result_intro_btn_employee);
        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ResultActivity) getActivity()).setCurrentPage(1, true);
            }
        });

        AppCompatButton btnEmployer = (AppCompatButton) view.findViewById(R.id.result_intro_btn_employer);
        btnEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ResultActivity) getActivity()).setCurrentPage(2, true);
            }
        });
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
            instance.getActivity().setTitle(getResources().getString(R.string.result_intro_title));
        }
    }

}