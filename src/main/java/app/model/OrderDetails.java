package app.model;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


// Class to store details about specific order details
public class OrderDetails extends RecursiveTreeObject<OrderDetails> {
    public StringProperty mCode;
    public StringProperty description;
    public StringProperty expected;
    public StringProperty bookedIn;

    public OrderDetails(String mCode, String description, String expected, String bookedIn) {
        this.mCode = new SimpleStringProperty(mCode);
        this.description = new SimpleStringProperty(description);
        this.expected = new SimpleStringProperty(expected);
        this.bookedIn = new SimpleStringProperty(bookedIn);
    }
}