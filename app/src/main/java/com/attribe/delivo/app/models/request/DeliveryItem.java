package com.attribe.delivo.app.models.request;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */


public class DeliveryItem
{
    private String name;
    private String detail;
    private String phone;

    private String pick_nearby;


    private String drop_nearby;

    private String pick_address;
    private String drop_address;


    private double drop_lng;

    private double drop_lat;

    private double pick_lat;
    private double pick_lng;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPick_nearby() {
        return pick_nearby;
    }

    public void setPick_nearby(String pick_nearby) {
        this.pick_nearby = pick_nearby;
    }

    public String getDrop_nearby() {
        return drop_nearby;
    }

    public void setDrop_nearby(String drop_nearby) {
        this.drop_nearby = drop_nearby;
    }

    public String getPick_address() {
        return pick_address;
    }

    public void setPick_address(String pick_address) {
        this.pick_address = pick_address;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public void setDrop_address(String drop_address) {
        this.drop_address = drop_address;
    }

    public double getDrop_lng() {
        return drop_lng;
    }

    public void setDrop_lng(double drop_lng) {
        this.drop_lng = drop_lng;
    }

    public double getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(double drop_lat) {
        this.drop_lat = drop_lat;
    }

    public double getPick_lat() {
        return pick_lat;
    }

    public void setPick_lat(double pick_lat) {
        this.pick_lat = pick_lat;
    }

    public double getPick_lng() {
        return pick_lng;
    }

    public void setPick_lng(double pick_lng) {
        this.pick_lng = pick_lng;
    }





}
