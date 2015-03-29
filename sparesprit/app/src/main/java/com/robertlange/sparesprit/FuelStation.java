/**
 * FuelStation.java
 * 
 * (c) Robert Lange
 * Alle Rechte beim Autor.
 */
package com.robertlange.sparesprit;

import android.content.Context;

import java.text.DecimalFormat;

/**
 * Diese Klasse speichert Informationen ueber eine Tankstelle:
 *
 * Die Methode {@link #getIdForDrawable()} liefert einen Wert, der fuer das
 * Zeichnen der Tankstelle verwendet werden kann. Damit das funktioniert,
 * muss die Tankstelle aus {@code R.string} belegt werden,
 * 
 * @author Robert Lange
 * @see FuelStations
 * 
 */
public final class FuelStation {

    private java.lang.Integer id;

    private int owner;

    private boolean isOpen;

    private java.lang.String openFrom;

    private java.lang.String openTo;

    private com.robertlange.sparesprit.Location location;

    private float price;

    private com.robertlange.sparesprit.Address address;

    private java.lang.String reporttime;

    private float distance;

    public FuelStation() {
    };

    public FuelStation(
            java.lang.Integer id,
            int owner,
            boolean isOpen,
            java.lang.String openFrom,
            java.lang.String openTo,
            com.robertlange.sparesprit.Location location,
            float price,
            com.robertlange.sparesprit.Address address,
            java.lang.String reporttime,
            float distance) {
        this.id = id;
        this.owner = owner;
        this.isOpen = isOpen;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.location = location;
        this.price = price;
        this.address = address;
        this.reporttime = reporttime;
        this.distance = distance;
    }


    /**
     * Gets the id value for this PetrolStation.
     *
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this PetrolStation.
     *
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the owner value for this PetrolStation.
     *
     * @return owner
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Sets the id value for this PetrolStation.
     *
     * @param owner
     */
    public void setOwner(int owner) {
        this.owner = owner;
    }


    /**
     * Gets the isOpen value for this PetrolStation.
     *
     * @return isOpen
     */
    public boolean isIsOpen() {
        return isOpen;
    }


    /**
     * Sets the isOpen value for this PetrolStation.
     *
     * @param isOpen
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }


    /**
     * Gets the openFrom value for this PetrolStation.
     *
     * @return openFrom
     */
    public java.lang.String getOpenFrom() {
        return openFrom;
    }


    /**
     * Sets the openFrom value for this PetrolStation.
     *
     * @param openFrom
     */
    public void setOpenFrom(java.lang.String openFrom) {
        this.openFrom = openFrom;
    }


    /**
     * Gets the openTo value for this PetrolStation.
     *
     * @return openTo
     */
    public java.lang.String getOpenTo() {
        return openTo;
    }


    /**
     * Sets the openTo value for this PetrolStation.
     *
     * @param openTo
     */
    public void setOpenTo(java.lang.String openTo) {
        this.openTo = openTo;
    }


    /**
     * Gets the location value for this PetrolStation.
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }


    /**
     * Sets the location value for this PetrolStation.
     *
     * @param location
     */
    public void setLocation(com.robertlange.sparesprit.Location location) {
        this.location = location;
    }


    /**
     * Gets the price value for this PetrolStation.
     *
     * @return price
     */
    public float getPrice() {
        return price;
    }


    /**
     * Sets the price value for this PetrolStation.
     *
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }


    /**
     * Gets the address value for this PetrolStation.
     *
     * @return address
     */
    public com.robertlange.sparesprit.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this PetrolStation.
     *
     * @param address
     */
    public void setAddress(com.robertlange.sparesprit.Address address) {
        this.address = address;
    }


    /**
     * Gets the reporttime value for this PetrolStation.
     *
     * @return reporttime
     */
    public java.lang.String getReporttime() {
        return reporttime;
    }


    /**
     * Sets the reporttime value for this PetrolStation.
     *
     * @param reporttime
     */
    public void setReporttime(java.lang.String reporttime) {
        this.reporttime = reporttime;
    }


    /**
     * Gets the distance value for this PetrolStation.
     *
     * @return distance
     */
    public float getDistance() {
        return distance;
    }


    /**
     * Sets the distance value for this PetrolStation.
     *
     * @param distance
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FuelStation)) return false;
        FuelStation other = (FuelStation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.id==null && other.getId()==null) ||
                        (this.id!=null &&
                                this.id.equals(other.getId()))) &&
                ((this.getOwner() ==0 && other.getOwner()==0) ||
                        (this.getOwner() !=0 &&
                                this.getOwner() == other.getOwner())) &&
                this.isOpen == other.isIsOpen() &&
                ((this.openFrom==null && other.getOpenFrom()==null) ||
                        (this.openFrom!=null &&
                                this.openFrom.equals(other.getOpenFrom()))) &&
                ((this.openTo==null && other.getOpenTo()==null) ||
                        (this.openTo!=null &&
                                this.openTo.equals(other.getOpenTo()))) &&
                ((this.location==null && other.getLocation()==null) ||
                        (this.location!=null &&
                                this.location.equals(other.getLocation()))) &&
                this.price == other.getPrice() &&
                ((this.address==null && other.getAddress()==null) ||
                        (this.address!=null &&
                                this.address.equals(other.getAddress()))) &&
                ((this.reporttime==null && other.getReporttime()==null) ||
                        (this.reporttime!=null &&
                                this.reporttime.equals(other.getReporttime()))) &&
                this.distance == other.getDistance();
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getOwner() != 0) {
            _hashCode += getOwner();
        }
        _hashCode += (isIsOpen() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getOpenFrom() != null) {
            _hashCode += getOpenFrom().hashCode();
        }
        if (getOpenTo() != null) {
            _hashCode += getOpenTo().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        _hashCode += new Float(getPrice()).hashCode();
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getReporttime() != null) {
            _hashCode += getReporttime().hashCode();
        }
        _hashCode += new Float(getDistance()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

	public String getName(Context context) {
		return context.getString(getOwner());
	}

	/**
	 * Liefert einen Wert aus {@code R.drawable}, der fuer das Zeichnen der
	 * Tankstellensymbols verwendet werden kann.
	 * 
	 * @return Wert aus {@code R.drawable}
	 */
	public int getIdForDrawable() {
		switch (getOwner()) {
		case R.string.agip:
			return R.drawable.agip;
        case R.string.agravis:
            return R.drawable.agravis;
        case R.string.allguth:
            return R.drawable.allguth;
        case R.string.aral:
            return R.drawable.aral;
        case R.string.avia:
            return R.drawable.avia;
        case R.string.bavaria:
            return R.drawable.bavaria;
        case R.string.baywa:
            return R.drawable.baywa;
        case R.string.bp:
            return R.drawable.bp;
        case R.string.ed:
            return R.drawable.ed;
        case R.string.esso:
            return R.drawable.esso;
        case R.string.hem:
            return R.drawable.hem;
        case R.string.hoyer:
            return R.drawable.hoyer;
        case R.string.jet:
            return R.drawable.jet;
        case R.string.oil:
            return R.drawable.oil;
        case R.string.omv:
            return R.drawable.omv;
        case R.string.orlen:
            return R.drawable.orlen;
        case R.string.q1:
            return R.drawable.q1;
        case R.string.shell:
            return R.drawable.shell;
        case R.string.star:
            return R.drawable.star;
        case R.string.station:
            return R.drawable.station;
        case R.string.total:
            return R.drawable.total;
        case R.string.turmoel:
            return R.drawable.turmoel;
        }
		// ggf. ein Standardbild liefern
		return R.drawable.station;
	}
}
