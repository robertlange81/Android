package sageone.abacus;

import retrofit.Response;
import sageone.abacus.Exceptions.StatusCodeException;
import sageone.abacus.Exceptions.WebServiceFailureException;
import sageone.abacus.Interfaces.AbacusApiInterface;
import sageone.abacus.Interfaces.ApiCallbackListener;
import sageone.abacus.Models.Calculation;
import sageone.abacus.Models.CalculationData;
import sageone.abacus.Models.CalculationInputData;
import sageone.abacus.Models.Insurances;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.ConnectionSpec;

import java.lang.reflect.Array;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by otomaske on 27.01.2016.
 */
public class WebService
{
    private RetrofitRestClient retrofitClient;
    private AbacusApiInterface apiService;
    private ApiCallbackListener webserviceListener;
    private static WebService Instance;
    private Context context;

    public static synchronized WebService getInstance(Context c, ApiCallbackListener listener)
    {
        if (null == Instance) {
            Instance = new WebService(c, listener);
        }
        return Instance;
    }


    /**
     * The constructor.
     */
    private WebService(Context c, ApiCallbackListener listener)
    {
        context = c;
        webserviceListener = listener;

        init();
    }


    /**
     * Initialize the rest client
     * and the instance web service.
     */
    private void init()
    {
        String apiUriBase = context.getResources().getString(R.string.api_uri_base);

        retrofitClient = new RetrofitRestClient();
        apiService = retrofitClient.RetrofitRestClient(apiUriBase).create(AbacusApiInterface.class);
    }


    /**
     * Calculates given data via web service call.
     *
     * @param data
     */
    public void Calculate(CalculationInputData data)
    {
        Call<Calculation> call = apiService.Calc(data);

        Log.d("ServiceCall", "Initialize calculation ..");
        call.enqueue(new Callback<Calculation>() {
            @Override
            public void onResponse(Response<Calculation> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
                    new StatusCodeException();
                }
                webserviceListener.responseFinishCalculation(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ServiceError", "Failure on calculation. " + t.getMessage().toString());
                new WebServiceFailureException();
            }
        });
    }


    /**
     * Fetch all available Health Insurances
     * by calling web service via rest client.
     *
     * @throws StatusCodeException
     */
    public void Insurances() throws StatusCodeException, WebServiceFailureException
    {
        Call<Insurances> call = apiService.Insurances();

        call.enqueue(new Callback<Insurances>() {
            @Override
            public void onResponse(Response<Insurances> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
                    new StatusCodeException();
                }
                webserviceListener.responseFinishInsurances(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                new WebServiceFailureException();
            }
        });
    }

}
