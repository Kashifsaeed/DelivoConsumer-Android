package models.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabih Ahmed on 08-Jun-16.
 */
public class ResponseNewOrder {

    public Meta meta;
    public Data data;


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

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
        private String clientId;
        private String clientName;
        private List<DeleveryOrderItem> deleveryOrderItem = new ArrayList<DeleveryOrderItem>();
        private Object guestUserId;

        public Data(String orderid, String description, String createdby, String createddate,
                    Object updatedBy, Object updatedDate, String sourceaddress, Object sourceLocation,
                    String destinatioaddress, Object destinationLocation, Object assignto, String status,
                    Object completeOrderCount, String clientId, String clientName, List<DeleveryOrderItem> deleveryOrderItem,
                    Object guestUserId) {
            this.orderid = orderid;
            this.description = description;
            this.createdby = createdby;
            this.createddate = createddate;
            this.updatedBy = updatedBy;
            this.updatedDate = updatedDate;
            this.sourceaddress = sourceaddress;
            this.sourceLocation = sourceLocation;
            this.destinatioaddress = destinatioaddress;
            this.destinationLocation = destinationLocation;
            this.assignto = assignto;
            this.status = status;
            this.completeOrderCount = completeOrderCount;
            this.clientId = clientId;
            this.clientName = clientName;
            this.deleveryOrderItem = deleveryOrderItem;
            this.guestUserId = guestUserId;
        }

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

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
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

        public DeleveryOrderItem(Object orderId, SourceLocation sourceLocation,
                                 DestinationLocation destinationLocation, Object collectDate,
                                 Object collectTime, Object deliverDate, Object deliverTime,
                                 Object sourceLocationId, Object destinationLocationId, Object quoteId,
                                 Object version, String status, String createdBy, String createdDate,
                                 String updatedBy, Object updatedDate) {
            this.orderId = orderId;
            this.sourceLocation = sourceLocation;
            this.destinationLocation = destinationLocation;
            this.collectDate = collectDate;
            this.collectTime = collectTime;
            this.deliverDate = deliverDate;
            this.deliverTime = deliverTime;
            this.sourceLocationId = sourceLocationId;
            this.destinationLocationId = destinationLocationId;
            this.quoteId = quoteId;
            this.version = version;
            this.status = status;
            this.createdBy = createdBy;
            this.createdDate = createdDate;
            this.updatedBy = updatedBy;
            this.updatedDate = updatedDate;
        }

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

        public DestinationLocation(double latitude, double longitude,
                                   String address, Object addressSec, Object createdDate,
                                   Object createdBy, Object udpatedDate, Object updatedBy) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
            this.addressSec = addressSec;
            this.createdDate = createdDate;
            this.createdBy = createdBy;
            this.udpatedDate = udpatedDate;
            this.updatedBy = updatedBy;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
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

        private double latitude;
        private double longitude;
        private String address;
        private Object addressSec;
        private Object createdDate;
        private Object createdBy;
        private Object udpatedDate;
        private Object updatedBy;




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


        public SourceLocation(double latitude, double longitude,
                              String address, Object addressSec, Object createdDate,
                              Object createdBy, Object udpatedDate, Object updatedBy) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
            this.addressSec = addressSec;
            this.createdDate = createdDate;
            this.createdBy = createdBy;
            this.udpatedDate = udpatedDate;
            this.updatedBy = updatedBy;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
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


}
