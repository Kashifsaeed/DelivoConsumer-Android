package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabih Ahmed on 08-Jun-16.
 */
public class NewOrder {

//    public String clientName;
//    public String description;
//    public String guestUserId;
//    public List<DeleveryOrderItem> deleveryOrderItem = new ArrayList<DeleveryOrderItem>();

    private String description;
    private String sourceaddress;
    private String destinatioaddress;
    private List<DeleveryOrderItem> deleveryOrderItem = new ArrayList<DeleveryOrderItem>();


    public NewOrder(String description, String sourceaddress, String destinatioaddress, List<DeleveryOrderItem> deleveryOrderItem) {
        this.description = description;
        this.sourceaddress = sourceaddress;
        this.destinatioaddress = destinatioaddress;
        this.deleveryOrderItem = deleveryOrderItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceaddress() {
        return sourceaddress;
    }

    public void setSourceaddress(String sourceaddress) {
        this.sourceaddress = sourceaddress;
    }

    public String getDestinatioaddress() {
        return destinatioaddress;
    }

    public void setDestinatioaddress(String destinatioaddress) {
        this.destinatioaddress = destinatioaddress;
    }

    public List<DeleveryOrderItem> getDeleveryOrderItem() {
        return deleveryOrderItem;
    }

    public void setDeleveryOrderItem(List<DeleveryOrderItem> deleveryOrderItem) {
        this.deleveryOrderItem = deleveryOrderItem;
    }


//    public String getClientName() {
//        return clientName;
//    }
//
//    public void setClientName(String clientName) {
//        this.clientName = clientName;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getGuestUserId() {
//        return guestUserId;
//    }
//
//    public void setGuestUserId(String guestUserId) {
//        this.guestUserId = guestUserId;
//    }
//
//    public List<DeleveryOrderItem> getDeleveryOrderItem() {
//        return deleveryOrderItem;
//    }
//
//    public void setDeleveryOrderItem(List<DeleveryOrderItem> deleveryOrderItem) {
//        this.deleveryOrderItem = deleveryOrderItem;
//    }
}
