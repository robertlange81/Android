/**
 * FuelStations.java
 * 
 * (c) Robert Lange 2015
 * Alle Rechte beim Autor.
 */
package sageone.abacus.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import sageone.abacus.R;

/**
 * Diese Klasse bildet alle erhaltenen Tankstellen in eine {@link Hashtable} ab,
 * deren Schluessel die ID ist, wobei jeder Tankstelle ein Logo zugeordnet wird
 * 
 * @author Robert Lange
 * 
 */
public final class FuelStations implements Serializable /* extends Hashtable<Integer, StationList> */ {

	// private static final long serialVersionUID = 6568074816645233035L;
	private static final FuelStations INSTANCE = new FuelStations();

	private FuelStations() {
		super();
	}

    public List<StationList> stationList = null;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<StationList> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationList> stationList) {
        this.stationList = stationList;
    }
/*
    public static void setFuelStations(List<StationList> fs) {
        INSTANCE.clear();
        for(StationList v : fs)
            INSTANCE.put(v.getId(), v);
    }

    public static void setFuelStation(StationList fs) {
        INSTANCE.put(fs.getId(), fs);
    }

	/**
	 * Liefert eine Referenz auf ein Element der Hashtable, die die Tankstelle
	 * der uebergebenen ID repraesentiert.
	 * 
	 * @param id
	 * @return Instanz eines {@link FuelStation}s

	public static StationList getFuelStationById(int id) {
        return INSTANCE.get(id);
	}

    /**
     * Liefert eine Referenz auf ein Element der Hashtable, die die Tankstelle
     * der uebergebenen ID repraesentiert.
     *
     * @return Instanz eines {@link FuelStation}s
     */
    /*
    public static ArrayList<StationList> getAll() {
        return new ArrayList<StationList>(INSTANCE.values());
    }*/

    public static int matchOwner(String owner) {
        String o = owner.toLowerCase();
        if(o.contains("agip"))
                return R.string.agip;
        if(o.contains("agravis"))
                return R.string.agravis;
        if(o.contains("allguth"))
                return R.string.allguth;
        if(o.contains("aral"))
                return R.string.aral;
        if(o.contains("avia"))
                return R.string.avia;
        if(o.contains("bavaria"))
                return R.string.bavaria;
        if(o.contains("baywa"))
                return R.string.baywa;
        if(o.contains("bp"))
                return R.string.bp;
        if(o.contains("ed"))
                return R.string.ed;
        if(o.contains("esso"))
                return R.string.esso;
        if(o.contains("hem"))
                return R.string.hem;
        if(o.contains("hoyer"))
                return R.string.hoyer;
        if(o.contains("jet"))
                return R.string.jet;
        if(o.contains("oil"))
                return R.string.oil;
        if(o.contains("omv"))
                return R.string.omv;
        if(o.contains("orlen"))
                return R.string.orlen;
        if(o.contains("q1"))
                return R.string.q1;
        if(o.contains("shell"))
                return R.string.shell;
        if(o.contains("star"))
                return R.string.star;
        if(o.contains("station"))
                return R.string.station;
        if(o.contains("total"))
                return R.string.total;
        if(o.contains("turmoel"))
                return R.string.turmoel;

        return 1;
    }
}