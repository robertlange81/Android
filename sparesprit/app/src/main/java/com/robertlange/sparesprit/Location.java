package com.robertlange.sparesprit;

/**
 * Created by rlange on 02.02.2015.
 */
/**
 * Location.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class Location  implements java.io.Serializable {
    private double latitude;

    private double longitude;

    public Location() {
    }

    public Location(
            double latitude,
            double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Gets the latitude value for this Location.
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this Location.
     *
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the longitude value for this Location.
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this Location.
     *
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

