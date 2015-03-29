package com.robertlange.sparesprit;

/**
 * Created by rlange on 08.03.2015.
 */
/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class Address  implements java.io.Serializable {
    private java.lang.String street;

    private java.lang.String housenumber;

    private java.lang.String postal;

    private java.lang.String place;

    public Address() {
    }

    public Address(
            java.lang.String street,
            java.lang.String housenumber,
            java.lang.String postal,
            java.lang.String place) {
        this.street = street;
        this.housenumber = housenumber;
        this.postal = postal;
        this.place = place;
    }


    /**
     * Gets the street value for this Address.
     *
     * @return street
     */
    public java.lang.String getStreet() {
        return street;
    }


    /**
     * Sets the street value for this Address.
     *
     * @param street
     */
    public void setStreet(java.lang.String street) {
        this.street = street;
    }


    /**
     * Gets the housenumber value for this Address.
     *
     * @return housenumber
     */
    public java.lang.String getHousenumber() {
        return housenumber;
    }


    /**
     * Sets the housenumber value for this Address.
     *
     * @param housenumber
     */
    public void setHousenumber(java.lang.String housenumber) {
        this.housenumber = housenumber;
    }


    /**
     * Gets the postal value for this Address.
     *
     * @return postal
     */
    public java.lang.String getPostal() {
        return postal;
    }


    /**
     * Sets the postal value for this Address.
     *
     * @param postal
     */
    public void setPostal(java.lang.String postal) {
        this.postal = postal;
    }


    /**
     * Gets the place value for this Address.
     *
     * @return place
     */
    public java.lang.String getPlace() {
        return place;
    }


    /**
     * Sets the place value for this Address.
     *
     * @param place
     */
    public void setPlace(java.lang.String place) {
        this.place = place;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.street==null && other.getStreet()==null) ||
                        (this.street!=null &&
                                this.street.equals(other.getStreet()))) &&
                ((this.housenumber==null && other.getHousenumber()==null) ||
                        (this.housenumber!=null &&
                                this.housenumber.equals(other.getHousenumber()))) &&
                ((this.postal==null && other.getPostal()==null) ||
                        (this.postal!=null &&
                                this.postal.equals(other.getPostal()))) &&
                ((this.place==null && other.getPlace()==null) ||
                        (this.place!=null &&
                                this.place.equals(other.getPlace())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getStreet() != null) {
            _hashCode += getStreet().hashCode();
        }
        if (getHousenumber() != null) {
            _hashCode += getHousenumber().hashCode();
        }
        if (getPostal() != null) {
            _hashCode += getPostal().hashCode();
        }
        if (getPlace() != null) {
            _hashCode += getPlace().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
}
