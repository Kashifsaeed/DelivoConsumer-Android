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

        public String orderid;
        public String description;
        public String createdby;
        public String createddate;
        public Object sourceaddress;
        public Object destinatioaddress;
        public Object assignto;
        public Boolean iscompleted;
        public Object clientId;
        public String clientName;
        public List<DeleveryOrderItem> deleveryOrderItem = new ArrayList<DeleveryOrderItem>();
        public String guestUserId;


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

        public Object getSourceaddress() {
            return sourceaddress;
        }

        public void setSourceaddress(Object sourceaddress) {
            this.sourceaddress = sourceaddress;
        }

        public Object getDestinatioaddress() {
            return destinatioaddress;
        }

        public void setDestinatioaddress(Object destinatioaddress) {
            this.destinatioaddress = destinatioaddress;
        }

        public Object getAssignto() {
            return assignto;
        }

        public void setAssignto(Object assignto) {
            this.assignto = assignto;
        }

        public Boolean getIscompleted() {
            return iscompleted;
        }

        public void setIscompleted(Boolean iscompleted) {
            this.iscompleted = iscompleted;
        }

        public Object getClientId() {
            return clientId;
        }

        public void setClientId(Object clientId) {
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

        public String getGuestUserId() {
            return guestUserId;
        }

        public void setGuestUserId(String guestUserId) {
            this.guestUserId = guestUserId;
        }
    }







    public class DeleveryOrderItem {

        public Object orderid;
        public String sourceAddress;
        public Object sourceAddressSec;
        public String destinatioAddress;
        public Object destinatioAddressSec;
        public Object collectDate;
        public Object collectTime;
        public Object deliverDate;
        public Object deliverTime;
        public Object sourceLocationId;
        public Object destinationLocationId;
        public Object orderId;
        public Object quoteId;
        public Object version;
        public Object status;
        public String createdBy;
        public Object createdDate;
        public Object updatedBy;
        public Object updatedDate;

    }




}
