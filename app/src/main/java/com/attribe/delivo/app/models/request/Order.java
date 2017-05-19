package com.attribe.delivo.app.models.request;

/**
 * Author: Uzair Qureshi
 * Date:  5/11/17.
 * Description:
 */

public class Order {
    private String pickup_date;

    private float totaldistance;

    private String drop_date;

    private String drop_time;

    private String pickup_time;

    private String order_type="time_critical";




    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public float getTotaldistance() {
        return totaldistance;
    }

    public void setTotaldistance(float totaldistance) {
        this.totaldistance = totaldistance;
    }

    public String getDrop_date() {
        return drop_date;
    }

    public void setDrop_date(String drop_date) {
        this.drop_date = drop_date;
    }

    public String getDrop_time() {
        return drop_time;
    }

    public void setDrop_time(String drop_time) {
        this.drop_time = drop_time;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
