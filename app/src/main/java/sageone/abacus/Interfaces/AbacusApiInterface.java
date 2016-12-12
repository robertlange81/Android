package sageone.abacus.Interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sageone.abacus.Models.StationList;
import sageone.abacus.Models.InputData;
import sageone.abacus.Models.Insurances;
import sageone.abacus.Models.LocationData;
import sageone.abacus.Models.TownData;

/**
 * Created by otomaske on 04.02.2016.
 */
public interface AbacusApiInterface
{
    @GET("insurances")
    Call<Insurances> Insurances();

    @GET("FuelApi.php?request=Coordsbytown")
    Call<LocationData> Location(@Query("town") String loc);

    //@GET("FuelApi.php?request=townbycoords&latitude=51.3493&longitude=12.39392")
    @GET("FuelApi.php?request=townbycoords")
    Call<TownData> TownByCoords(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("success")
    Call<StationList> Success();

    @POST("FuelApi.php?request=databycoords")
    Call<StationList> DataByCoords(@Body InputData data);
}
