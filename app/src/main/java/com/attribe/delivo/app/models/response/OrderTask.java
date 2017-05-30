package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  5/24/17.
 * Description:
 */

public class OrderTask {
    private int id;
    private String taskType;
    private String details;
    private String address;
    private String nearby;
    private Double lat;
    private Double lng;
    private String name;
    private String contact;
    private String time;
    private Object itemType;
    private Integer orderId;
    private String createdAt;
    private String updatedAt;
    private Object riderId;
    private Integer status;
    private int sortingOrder;
    private String signatureUrl;
    private Boolean payAtPickup;
    private Object payAtPickupAmount;
    private boolean billed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNearby() {
        return nearby;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getItemType() {
        return itemType;
    }

    public void setItemType(Object itemType) {
        this.itemType = itemType;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Object getRiderId() {
        return riderId;
    }

    public void setRiderId(Object riderId) {
        this.riderId = riderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(int sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public Boolean getPayAtPickup() {
        return payAtPickup;
    }

    public void setPayAtPickup(Boolean payAtPickup) {
        this.payAtPickup = payAtPickup;
    }

    public Object getPayAtPickupAmount() {
        return payAtPickupAmount;
    }

    public void setPayAtPickupAmount(Object payAtPickupAmount) {
        this.payAtPickupAmount = payAtPickupAmount;
    }

    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
    }
}
