package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabih Ahmed on 08-Jun-16.
 */
public class NewOrder {

    private String description;
    private String sourceaddress;
    private String destinatioaddress;
    private List<DeleveryOrderItem> deleveryOrderItem = new ArrayList<DeleveryOrderItem>();

    public NewOrder() {
    }

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

    public class DeleveryOrderItem {

        private SourceLocation sourceLocation;
        private DestinationLocation destinationLocation;

        public DeleveryOrderItem() {
        }

        public DeleveryOrderItem(SourceLocation sourceLocation, DestinationLocation destinationLocation) {
            this.sourceLocation = sourceLocation;
            this.destinationLocation = destinationLocation;
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

    }

    public class DestinationLocation {


        private String latitude;
        private String longitude;
        private String address;

        public DestinationLocation() {
        }

        public DestinationLocation(String latitude, String longitude, String address) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

    public class SourceLocation {

        private String latitude;
        private String longitude;
        private String address;

        public SourceLocation() {
        }

        public SourceLocation(String latitude, String longitude, String address) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


    }

    }
