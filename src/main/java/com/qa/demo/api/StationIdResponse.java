package com.qa.demo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to map json response to station Id
 * 
 * @author deenesh
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationIdResponse {

    @JsonProperty(value = "alt_fuel_station")
    private StationDetailsBean details;

    public StationDetailsBean getDetails() {
        return details;
    }

    public void setDetails(StationDetailsBean details) {
        this.details = details;
    }

}