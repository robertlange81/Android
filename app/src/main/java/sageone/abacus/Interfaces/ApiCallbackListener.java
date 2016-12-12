package sageone.abacus.Interfaces;

import sageone.abacus.Models.StationList;
import sageone.abacus.Models.Insurances;
import sageone.abacus.Models.LocationData;
import sageone.abacus.Models.TownData;

/**
 * Created by otomaske on 04.02.2016.
 */
public interface ApiCallbackListener
{
    void responseFinishInsurances(Insurances insurances);
    void responseFailedInsurances(String message);
    void responseFinishStations(StationList fuelStations);
    void responseFailedStations(String message);

    void responseFinishLocation(LocationData location);
    void responseFailedLocation(String message);

    void responseFinishTown(TownData location);
    void responseFailedTown(String message);
}
