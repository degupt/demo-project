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
    private StationDetails details;

    public StationDetails getDetails() {
        return details;
    }

    public void setDetails(StationDetails details) {
        this.details = details;
    }

}