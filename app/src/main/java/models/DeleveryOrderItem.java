package models;

/**
 * Created by Sabih Ahmed on 08-Jun-16.
 */
public class DeleveryOrderItem {

    private String sourceAddress;
    private String destinatioAddress;
    private String createdBy;

    public DeleveryOrderItem(String sourceAddress, String destinatioAddress, String createdBy) {
        this.sourceAddress = sourceAddress;
        this.destinatioAddress = destinatioAddress;
        this.createdBy = createdBy;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinatioAddress() {
        return destinatioAddress;
    }

    public void setDestinatioAddress(String destinatioAddress) {
        this.destinatioAddress = destinatioAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
