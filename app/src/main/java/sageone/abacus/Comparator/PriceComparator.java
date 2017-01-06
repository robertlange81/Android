package sageone.abacus.Comparator;

import java.util.Comparator;

import sageone.abacus.Models.FuelStation;
import sageone.abacus.Models.StationList;

/**
 * Created by rlange on 16.03.2015.
 */
public class PriceComparator implements Comparator<StationList>{
    @Override
    public int compare(StationList a1, StationList a2) {
        return new Float(a1.getPrice()).compareTo(new Float(a2.getPrice()));
    }
}