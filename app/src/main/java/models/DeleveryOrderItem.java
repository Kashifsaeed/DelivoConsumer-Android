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

}
