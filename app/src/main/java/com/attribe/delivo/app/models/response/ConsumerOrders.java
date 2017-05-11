package com.attribe.delivo.app.models.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 8/15/2016.
 */
public class ConsumerOrders {

    private Meta meta;
    private List<UserData> data = new ArrayList<UserData>();
    private Object cursor;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

    public Object getCursor() {
        return cursor;
    }

    public void setCursor(Object cursor) {
        this.cursor = cursor;
    }



    public class UserData {


        private String orderid;
        private String description;
        private String createdby;
        private String createddate;
        private Object updatedBy;
        private Object updatedDate;
        private String sourceaddress;
        private Object sourceLocation;
        private String destinatioaddress;
        private Object destinationLocation;
        private Object assignto;
        private String status;
        private Object completeOrderCount;
        private Object clientId;
        private Object clientName;
        private List<DeleveryOrderItem> deleveryOrderItem = new ArrayList<DeleveryOrderItem>();
        private Object guestUserId;



        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(Object updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getSourceaddress() {
            return sourceaddress;
        }

        public void setSourceaddress(String sourceaddress) {
            this.sourceaddress = sourceaddress;
        }

        public Object getSourceLocation() {
            return sourceLocation;
        }

        public void setSourceLocation(Object sourceLocation) {
            this.sourceLocation = sourceLocation;
        }

        public String getDestinatioaddress() {
            return destinatioaddress;
        }

        public void setDestinatioaddress(String destinatioaddress) {
            this.destinatioaddress = destinatioaddress;
        }

        public Object getDestinationLocation() {
            return destinationLocation;
        }

        public void setDestinationLocation(Object destinationLocation) {
            this.destinationLocation = destinationLocation;
        }

        public Object getAssignto() {
            return assignto;
        }

        public void setAssignto(Object assignto) {
            this.assignto = assignto;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getCompleteOrderCount() {
            return completeOrderCount;
        }

        public void setCompleteOrderCount(Object completeOrderCount) {
            this.completeOrderCount = completeOrderCount;
        }

        public Object getClientId() {
            return clientId;
        }

        public void setClientId(Object clientId) {
            this.clientId = clientId;
        }

        public Object getClientName() {
            return clientName;
        }

        public void setClientName(Object clientName) {
            this.clientName = clientName;
        }

        public List<DeleveryOrderItem> getDeleveryOrderItem() {
            return deleveryOrderItem;
        }

        public void setDeleveryOrderItem(List<DeleveryOrderItem> deleveryOrderItem) {
            this.deleveryOrderItem = deleveryOrderItem;
        }

        public Object getGuestUserId() {
            return guestUserId;
        }

        public void setGuestUserId(Object guestUserId) {
            this.guestUserId = guestUserId;
        }

    }


    public class DeleveryOrderItem {

        private Object orderId;
        private SourceLocation sourceLocation;
        private DestinationLocation destinationLocation;
        private Object collectDate;
        private Object collectTime;
        private Object deliverDate;
        private Object deliverTime;
        private Object sourceLocationId;
        private Object destinationLocationId;
        private Object quoteId;
        private Object version;
        private String status;
        private String createdBy;
        private String createdDate;
        private String updatedBy;
        private Object updatedDate;


        public Object getOrderId() {
            return orderId;
        }

        public void setOrderId(Object orderId) {
            this.orderId = orderId;
        }

        public SourceLocation getSourceLocation() {
            return sourceLocation;
        }

        public void setSourceLocation(SourceLocation sourceLocation) {
            this.sourceLocation = sourceLocation;
        }

        public DestinationLocation getDestinationLocation() {
            return destinationLocation;
        }

        public void setDestinationLocation(DestinationLocation destinationLocation) {
            this.destinationLocation = destinationLocation;
        }

        public Object getCollectDate() {
            return collectDate;
        }

        public void setCollectDate(Object collectDate) {
            this.collectDate = collectDate;
        }

        public Object getCollectTime() {
            return collectTime;
        }

        public void setCollectTime(Object collectTime) {
            this.collectTime = collectTime;
        }

        public Object getDeliverDate() {
            return deliverDate;
        }

        public void setDeliverDate(Object deliverDate) {
            this.deliverDate = deliverDate;
        }

        public Object getDeliverTime() {
            return deliverTime;
        }

        public void setDeliverTime(Object deliverTime) {
            this.deliverTime = deliverTime;
        }

        public Object getSourceLocationId() {
            return sourceLocationId;
        }

        public void setSourceLocationId(Object sourceLocationId) {
            this.sourceLocationId = sourceLocationId;
        }

        public Object getDestinationLocationId() {
            return destinationLocationId;
        }

        public void setDestinationLocationId(Object destinationLocationId) {
            this.destinationLocationId = destinationLocationId;
        }

        public Object getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(Object quoteId) {
            this.quoteId = quoteId;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(Object updatedDate) {
            this.updatedDate = updatedDate;
        }

    }

    public class DestinationLocation {

        private double latitude;
        private double longitude;
        private String address;
        private Object addressSec;
        private Object createdDate;
        private Object createdBy;
        private Object udpatedDate;
        private Object updatedBy;


        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getAddressSec() {
            return addressSec;
        }

        public void setAddressSec(Object addressSec) {
            this.addressSec = addressSec;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUdpatedDate() {
            return udpatedDate;
        }

        public void setUdpatedDate(Object udpatedDate) {
            this.udpatedDate = udpatedDate;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

    }


    public class SourceLocation {


        private double latitude;
        private double longitude;
        private String address;
        private Object addressSec;
        private Object createdDate;
        private Object createdBy;
        private Object udpatedDate;
        private Object updatedBy;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getAddressSec() {
            return addressSec;
        }

        public void setAddressSec(Object addressSec) {
            this.addressSec = addressSec;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUdpatedDate() {
            return udpatedDate;
        }

        public void setUdpatedDate(Object udpatedDate) {
            this.udpatedDate = udpatedDate;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

    }

    public class Meta {


        private Boolean success;
        private Object apiDeprecated;
        private Object message;
        private Integer code;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Object getApiDeprecated() {
            return apiDeprecated;
        }

        public void setApiDeprecated(Object apiDeprecated) {
            this.apiDeprecated = apiDeprecated;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }


    }

    }
