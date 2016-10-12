package com.qa.demo.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This binder is to map the json response to nearest stations
 * 
 * @author deenesh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NearestStationsBean {

    @JsonProperty(value = "fuel_stations")
    private List<StationDetailsBean> details;

    /**
     * @return list of stations
     */
    public List<StationDetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<StationDetailsBean> details) {
        this.details = details;
    }

}