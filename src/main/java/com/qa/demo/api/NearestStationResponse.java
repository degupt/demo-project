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
public class NearestStationResponse {

    @JsonProperty(value = "fuel_stations")
    private List<StationDetails> details;

    /**
     * @return list of stations
     */
    public List<StationDetails> getDetails() {
        return details;
    }

    public void setDetails(List<StationDetails> details) {
        this.details = details;
    }

}