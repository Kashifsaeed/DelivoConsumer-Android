package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  5/11/17.
 * Description:
 */

public class Task {
    private float pay_at_pickup_amount;

    private String details;

    private String status="901";

    private String address;

    private String name;

    private boolean task_type;

    private float lng;

    private String nearby;

    private String contact;

    private float lat;

    public float getPay_at_pickup_amount() {
        return pay_at_pickup_amount;
    }

    public void setPay_at_pickup_amount(float pay_at_pickup_amount) {
        this.pay_at_pickup_amount = pay_at_pickup_amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getTask_type() {
        return task_type;
    }

    public void setTask_type(boolean task_type) {
        this.task_type = task_type;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getNearby() {
        return nearby;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
