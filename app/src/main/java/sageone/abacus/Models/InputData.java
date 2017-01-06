package sageone.abacus.Models;

import java.util.Date;

import sageone.abacus.Helper.CalculationInputHelper;

/**
 * Created by otomaske on 09.02.2016.
 *
 * The calculation request data.
 */
public class InputData {
    public String latitude;
    public String longitude;
    public String article;
    public String distance;
    public String sortBy;

    public InputData() {
        latitude    = "51.333333333333336";
        longitude   = "12.314076706914824";
        article     = "fuelPriceE5";
        distance    = "40";
        sortBy      = "Price";
    }
}
