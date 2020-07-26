package app.model;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


// Class to store details about specific order details
public class OrderDetails extends RecursiveTreeObject<OrderDetails> {

    private final StringProperty mCode;

    private final StringProperty description;

    private final StringProperty expected;

    private final StringProperty bookedIn;


    public OrderDetails(String mCode, String description, String expected, String bookedIn) {

        this.mCode = new SimpleStringProperty(mCode);
        this.description = new SimpleStringProperty(description);
        this.expected = new SimpleStringProperty(expected);
        this.bookedIn = new SimpleStringProperty(bookedIn);
    }


    public String getmCode() {

        return mCode.get();
    }


    public StringProperty mCodeProperty() {

        return mCode;
    }


    public String getDescription() {

        return description.get();
    }


    public StringProperty descriptionProperty() {

        return description;
    }


    public String getExpected() {

        return expected.get();
    }


    public StringProperty expectedProperty() {

        return expected;
    }


    public String getBookedIn() {

        return bookedIn.get();
    }


    public StringProperty bookedInProperty() {

        return bookedIn;
    }
}