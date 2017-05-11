package com.attribe.delivo.app.models;

/**
 * Created by attribe on 10/20/16.
 */
public class AgentsLocation {
    private Double lat;
    private Double lng;
    private String agent_id;
    private String infowindow;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getInfowindow() {
        return infowindow;
    }

    public void setInfowindow(String infowindow) {
        this.infowindow = infowindow;
    }


    public AgentsLocation()
    {

    }



}
