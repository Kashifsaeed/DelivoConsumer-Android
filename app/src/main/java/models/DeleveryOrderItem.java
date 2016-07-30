package models;

/**
 * Created by Sabih Ahmed on 08-Jun-16.
 */
public class DeleveryOrderItem {

    private SourceLocation sourceLocation;
    private DestinationLocation destinationLocation;

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


//    private String sourceAddress;
//    private String destinatioAddress;
//    private String createdBy;
//
//    public DeleveryOrderItem(String sourceAddress, String destinatioAddress, String createdBy) {
//        this.sourceAddress = sourceAddress;
//        this.destinatioAddress = destinatioAddress;
//        this.createdBy = createdBy;
//    }
//
//    public String getSourceAddress() {
//        return sourceAddress;
//    }
//
//    public void setSourceAddress(String sourceAddress) {
//        this.sourceAddress = sourceAddress;
//    }
//
//    public String getDestinatioAddress() {
//        return destinatioAddress;
//    }
//
//    public void setDestinatioAddress(String destinatioAddress) {
//        this.destinatioAddress = destinatioAddress;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
}
