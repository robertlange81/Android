/**
 * FuelStations.java
 * 
 * (c) Robert Lange 2015
 * Alle Rechte beim Autor.
 */
package com.robertlange.sparesprit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

/**
 * Diese Klasse bildet alle erhaltenen Tankstellen in eine {@link Hashtable} ab,
 * deren Schluessel die ID ist, wobei jeder Tankstelle ein Logo zugeordnet wird
 * 
 * @author Robert Lange
 * 
 */
public final class FuelStations extends Hashtable<Integer, FuelStation> {

	private static final long serialVersionUID = 6568074816645233035L;
	private static final FuelStations INSTANCE = new FuelStations();

	private FuelStations() {
		super();
	}

    public static void setFuelStations(List<FuelStation> fs) {
        INSTANCE.clear();
        for(FuelStation v : fs)
            INSTANCE.put(v.getId(), v);
    }

    public static void setFuelStation(FuelStation fs) {
        INSTANCE.put(fs.getId(), fs);
    }

	/**
	 * Liefert eine Referenz auf ein Element der Hashtable, die die Tankstelle
	 * der uebergebenen ID repraesentiert.
	 * 
	 * @param id
	 * @return Instanz eines {@link FuelStation}s
	 */
	public static FuelStation getFuelStationById(int id) {
        return INSTANCE.get(id);
	}

    /**
     * Liefert eine Referenz auf ein Element der Hashtable, die die Tankstelle
     * der uebergebenen ID repraesentiert.
     *
     * @return Instanz eines {@link FuelStation}s
     */
    public static ArrayList<FuelStation> getAll() {
        return new ArrayList<FuelStation>(INSTANCE.values());
    }

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

        return R.string.station;
    }
}