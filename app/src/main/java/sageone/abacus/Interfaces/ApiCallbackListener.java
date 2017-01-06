package sageone.abacus.Interfaces;

import java.util.List;

import sageone.abacus.Models.Example;
import sageone.abacus.Models.StationData;
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
    void responseFinishStations(Example fuelStations);
    void responseFailedStations(String message);

    void responseFinishLocation(LocationData location);
    void responseFailedLocation(String message);

    void responseFinishTown(TownData location);
    void responseFailedTown(String message);
}
