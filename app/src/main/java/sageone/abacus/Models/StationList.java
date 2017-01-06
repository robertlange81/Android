package sageone.abacus.Models;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

public class StationList {

    private String id;
    private String owner;
    private Boolean isOpen;
    private String openFrom;
    private String openTo;
    private Location location;
    private Double price;
    private Address address;
    private String reporttime;
    private Double distance;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getId() {
        return Integer.parseInt(id);
    }

    public void setId(String _int) {
        this.id = _int;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public Double getDistance() {
        return distance;
    }

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
