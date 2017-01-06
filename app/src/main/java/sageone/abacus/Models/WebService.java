package sageone.abacus.Models;

import retrofit2.Response;
import sageone.abacus.Extensions.RetrofitRestClient;
import sageone.abacus.Interfaces.AbacusApiInterface;
import sageone.abacus.Interfaces.ApiCallbackListener;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import sageone.abacus.R;

/**
 * Created by otomaske on 27.01.2016.
 */
public class WebService
{
    private RetrofitRestClient retrofitClient;
    private AbacusApiInterface apiService;
    private ApiCallbackListener webserviceListener;
    private Context context;
    private Call call;

    /**
     * The constructor.
     */
    public WebService(Context c, ApiCallbackListener listener)
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
        String proto = context.getResources().getString(R.string.api_uri_proto);
        String host = context.getResources().getString(R.string.api_uri_host);
        String url  = context.getResources().getString(R.string.api_uri_base);
        String credentials = null;
        int timeout = context.getResources().getInteger(R.integer.api_connection_timeout);

        String apiUriBase = proto + host + url;

        retrofitClient = new RetrofitRestClient();
        apiService = retrofitClient.RetrofitRestClient(apiUriBase, credentials, timeout)
                    .create(AbacusApiInterface.class);
    }


    /**
     * Calculates given data via web service call.
     *
     * @param data
     */
    public void GetStationList(InputWrapper data)
    {
        Log.v("ServiceCall", "Initialize calculation ..");
        call = apiService.DataByCoords(data.data);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                int code = response.code();

                if (response.isSuccess()) {
                    webserviceListener.responseFinishStations(response.body());
                } else {
                    webserviceListener.responseFailedStations(context.getResources().getString(R.string.exception_status_code));
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable throwable) {
                Log.e("WebService", "Failure on calculation. " + throwable.getStackTrace().toString());
                webserviceListener.responseFailedStations(context.getResources().getString(R.string.app_api_error));
            }
        });

    }

    /**
     * Fetch Coords
     * by calling web service via rest client.
     */
    public void Location(String loc)
    {
        Call<LocationData> call = apiService.Location(loc);

        call.enqueue(new Callback<LocationData>() {
            @Override
            public void onResponse(Call<LocationData> call, Response<LocationData> response) {

                int code = response.code();
                String message = null;

                switch (code) {
                    case 200:
                        webserviceListener.responseFinishLocation(response.body());
                        break;
                    case 401:
                        message = context.getResources().getString(R.string.exception_http_auth);
                        webserviceListener.responseFailedLocation(message);
                        break;
                    default:
                        message = context.getResources().getString(R.string.exception_status_code);
                        webserviceListener.responseFailedLocation(message);
                        break;
                }
            }

            @Override
            public void onFailure(Call<LocationData> call, Throwable t) {
                String err = t.getMessage().toString();
                webserviceListener.responseFailedInsurances(err);
            }
        });
    }

    /**
     * Fetch Town
     * by calling web service via rest client.
     */
    public void Town(double lat, double lon)
    {
        Call<TownData> call = apiService.TownByCoords(lat, lon);

        call.enqueue(new Callback<TownData>() {
            @Override
            public void onResponse(Call<TownData> call, Response<TownData> response) {

                int code = response.code();
                String message = null;

                switch (code) {
                    case 200:
                        webserviceListener.responseFinishTown(response.body());
                        break;
                    case 401:
                        message = context.getResources().getString(R.string.exception_http_auth);
                        webserviceListener.responseFailedTown(message);
                        break;
                    default:
                        message = context.getResources().getString(R.string.exception_status_code);
                        webserviceListener.responseFailedTown(message);
                        break;
                }
            }

            @Override
            public void onFailure(Call<TownData> call, Throwable t) {
                String err = t.getMessage().toString();
                webserviceListener.responseFailedInsurances(err);
            }
        });
    }


    /**
     * Cancel the active call.
     */
    public void cancel()
    {
        call.cancel();
    }

}
