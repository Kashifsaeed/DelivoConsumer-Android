package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  5/24/17.
 * Description:
 */

public class Order {
    private Integer id;
    private Object status;
    private Object urgent;
    private Object schedule;
    private Integer customerId;
    private String createdAt;
    private String updatedAt;
    private Integer companyId;
    private Integer totaldistance;
    private Object itemType;
    private String pickupTime;
    private String dropTime;
    private Object timeCritical;
    private String pickupDate;
    private String dropDate;
    private float total;
    private float cashPaid;
    private float cashReceived;
    private String orderType;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getUrgent() {
        return urgent;
    }

    public void setUrgent(Object urgent) {
        this.urgent = urgent;
    }

    public Object getSchedule() {
        return schedule;
    }

    public void setSchedule(Object schedule) {
        this.schedule = schedule;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getTotaldistance() {
        return totaldistance;
    }

    public void setTotaldistance(Integer totaldistance) {
        this.totaldistance = totaldistance;
    }

    public Object getItemType() {
        return itemType;
    }

    public void setItemType(Object itemType) {
        this.itemType = itemType;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public Object getTimeCritical() {
        return timeCritical;
    }

    public void setTimeCritical(Object timeCritical) {
        this.timeCritical = timeCritical;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDropDate() {
        return dropDate;
    }

    public void setDropDate(String dropDate) {
        this.dropDate = dropDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getCashPaid() {
        return cashPaid;
    }

    public void setCashPaid(float cashPaid) {
        this.cashPaid = cashPaid;
    }

    public float getCashReceived() {
        return cashReceived;
    }

    public void setCashReceived(float cashReceived) {
        this.cashReceived = cashReceived;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
