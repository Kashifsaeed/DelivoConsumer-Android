package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */

public class DeliveryRequest
{
    private String phone;

    private String detail;

    private double drop_lat;

    private double drop_lng;

    private double pick_lat;
    private double pick_lng;

    private int order_id;

    private String id;

    private String pick_nearby;

    private String updated_at;

    private String drop_nearby;

    private String pick_address;

    private String name;

    private String created_at;

    private String drop_address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(double drop_lat) {
        this.drop_lat = drop_lat;
    }

    public double getDrop_lng() {
        return drop_lng;
    }

    public void setDrop_lng(double drop_lng) {
        this.drop_lng = drop_lng;
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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPick_nearby() {
        return pick_nearby;
    }

    public void setPick_nearby(String pick_nearby) {
        this.pick_nearby = pick_nearby;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public void setDrop_address(String drop_address) {
        this.drop_address = drop_address;
    }
}
