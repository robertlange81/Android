package com.robertlange.sparesprit;
import java.util.Comparator;

/**
 * Created by rlange on 16.03.2015.
 */
public class DistanceComparator implements Comparator<FuelStation>{
    @Override
    public int compare(FuelStation a1, FuelStation a2) {
        return new Float(a1.getDistance()).compareTo(new Float(a2.getDistance()));
    }
}

