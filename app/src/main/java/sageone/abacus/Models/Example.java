package sageone.abacus.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rlange on 06.01.2017.
 */
public class Example {

    private List<StationList> stationList = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<StationList> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationList> stationList) {
        this.stationList = stationList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}