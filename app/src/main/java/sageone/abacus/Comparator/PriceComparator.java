package sageone.abacus.Comparator;

import java.util.Comparator;

import sageone.abacus.Models.FuelStation;

/**
 * Created by rlange on 16.03.2015.
 */
public class PriceComparator implements Comparator<FuelStation>{
    @Override
    public int compare(FuelStation a1, FuelStation a2) {
        return new Float(a1.getPrice()).compareTo(new Float(a2.getPrice()));
    }
}