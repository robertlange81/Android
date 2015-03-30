package com.robertlange.sparesprit;
import java.util.Comparator;

/**
 * Created by rlange on 16.03.2015.
 */
public class CombinedComparator implements Comparator<FuelStation>{
    @Override
    public int compare(FuelStation a1, FuelStation a2) {
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

