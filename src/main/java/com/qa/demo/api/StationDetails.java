package com.qa.demo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to map the json response for station details
 * 
 * @author deenesh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationDetails {

    @JsonProperty("station_name")
    private String stationName;
    @JsonProperty("street_address")
    private String streetAddress;
    private String id;
    private String city;
    private String state;
    private String zip;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "StationDetails [id=" + id + ", station_name=" + stationName + ", street_address=" + streetAddress
                + ", city=" + city + ", state=" + state + ", zip=" + zip + "]";
    }

}