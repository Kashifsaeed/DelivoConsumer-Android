package com.attribe.delivo.app.eventbus;

/**
 * Author: Uzair Qureshi
 * Date:  7/3/17.
 * Description:
 */

/**
 * Event Class
 */
public class PickEvent {
    private int pick_hour;
    private int pick_min;
    private String pick_time;


    private String pick_date;



    private boolean picknow;
    public PickEvent(int pick_hour, int pick_min) {
        this.pick_hour = pick_hour;
        this.pick_min = pick_min;
    }

    public PickEvent(int pick_hour, int pick_min, boolean picknow)
    {
        this.pick_hour = pick_hour;
        this.pick_min = pick_min;
        this.picknow = picknow;
    }
    public PickEvent(String pick_time,String pick_date,boolean picknow) {
        this.pick_time = pick_time;
        this.picknow=picknow;
        this.pick_date=pick_date;
    }


    public String getPick_time() {
        return pick_time;
    }

    public void setPick_time(String pick_time) {
        this.pick_time = pick_time;
    }







    public int getPick_hour() {
        return pick_hour;
    }

    public void setPick_hour(int pick_hour) {
        this.pick_hour = pick_hour;
    }

    public int getPick_min() {
        return pick_min;
    }

    public void setPick_min(int pick_min) {
        this.pick_min = pick_min;
    }
    public boolean isPicknow() {
        return picknow;
    }

    public void setPicknow(boolean picknow) {
        this.picknow = picknow;
    }
    public String getPick_date() {
        return pick_date;
    }

    public void setPick_date(String pick_date) {
        this.pick_date = pick_date;
    }





}
