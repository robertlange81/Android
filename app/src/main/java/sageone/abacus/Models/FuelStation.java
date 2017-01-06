package sageone.abacus.Models;


import java.util.HashMap;
import java.util.Map;

import sageone.abacus.R;

public class FuelStation {

    public String id;
    public String owner;
    public Boolean isOpen;
    public String openFrom;
    public String openTo;
    public Location location;
    public Double price;
    public Address address;
    public String reporttime;
    public Double distance;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return Integer.parseInt(id);
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     * The isOpen
     */
    public Boolean getIsOpen() {
        return isOpen;
    }

    /**
     *
     * @param isOpen
     * The isOpen
     */
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     *
     * @return
     * The openFrom
     */
    public String getOpenFrom() {
        return openFrom;
    }

    /**
     *
     * @param openFrom
     * The openFrom
     */
    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    /**
     *
     * @return
     * The openTo
     */
    public String getOpenTo() {
        return openTo;
    }

    /**
     *
     * @param openTo
     * The openTo
     */
    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    /**
     *
     * @return
     * The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The price
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The reporttime
     */
    public String getReporttime() {
        return reporttime;
    }

    /**
     *
     * @param reporttime
     * The reporttime
     */
    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    /**
     *
     * @return
     * The distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**
     * Liefert einen Wert aus {@code R.drawable}, der fuer das Zeichnen der
     * Tankstellensymbols verwendet werden kann.
     *
     * @return Wert aus {@code R.drawable}
     */
    /*
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
    }*/
}