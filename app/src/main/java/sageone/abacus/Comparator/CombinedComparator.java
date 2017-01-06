package sageone.abacus.Comparator;

import java.util.Comparator;

import sageone.abacus.Models.FuelStation;
import sageone.abacus.Models.StationList;

/**
 * Created by rlange on 16.03.2015.
 */
public class CombinedComparator implements Comparator<StationList>{
    @Override
    public int compare(StationList a1, StationList a2) {
        // costs for upkeeping and depreciation: ~50 cent per kilometer
        double upkeeping = 0.40;

        // average tank load in Liter
        double load = 40.0;

        if((load * (a1.getPrice()- a2.getPrice()))
                + (upkeeping * (a1.getDistance() - a2.getDistance())) > 0)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}