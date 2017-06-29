package com.attribe.delivo.app.models.response;

import java.util.List;

/**
 * Author: Uzair Qureshi
 * Date:  6/23/17.
 * Description:
 */

public class MyOrders {
    private Meta meta;
    private List<Datum> data = null;
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }



    public class Datum {
        private Integer id;
        private Double totaldistance;
        private String item_type;
        private String pickup_time;
        private String drop_time;
        private String pickup_date;
        private String drop_date;
        private Integer status;
        private Integer company_id;
        private Double cash_paid;
        private Double cash_received;
        private Double total;
        private Customer customer;
        private List<Task> tasks = null;
        private List<OrderCharges> charges = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getTotaldistance() {
            return totaldistance;
        }

        public void setTotaldistance(Double totaldistance) {
            this.totaldistance = totaldistance;
        }

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getPickup_time() {
            return pickup_time;
        }

        public void setPickup_time(String pickup_time) {
            this.pickup_time = pickup_time;
        }

        public String getDrop_time() {
            return drop_time;
        }

        public void setDrop_time(String drop_time) {
            this.drop_time = drop_time;
        }

        public String getPickup_date() {
            return pickup_date;
        }

        public void setPickup_date(String pickup_date) {
            this.pickup_date = pickup_date;
        }

        public String getDrop_date() {
            return drop_date;
        }

        public void setDrop_date(String drop_date) {
            this.drop_date = drop_date;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCompany_id() {
            return company_id;
        }

        public void setCompany_id(Integer company_id) {
            this.company_id = company_id;
        }

        public Double getCash_paid() {
            return cash_paid;
        }

        public void setCash_paid(Double cash_paid) {
            this.cash_paid = cash_paid;
        }

        public Double getCash_received() {
            return cash_received;
        }

        public void setCash_received(Double cash_received) {
            this.cash_received = cash_received;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public List<Task> getTasks() {
            return tasks;
        }

        public void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }

        public List<OrderCharges> getCharges() {
            return charges;
        }

        public void setCharges(List<OrderCharges> charges) {
            this.charges = charges;
        }
    }
}

