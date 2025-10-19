package model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.BookingDates;

public class BookingResponse {
    @JsonProperty(value = "bookingid", required = true)
    private int bookingId;
    @JsonProperty("roomid")
    private int roomId;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    private String email;
    private String phone;
    @JsonProperty("depositpaid")
    private boolean depositPaid;
    @JsonProperty("bookingdates")
    private BookingDates bookingDates;

    public BookingResponse() {}

    public int getBookingId() {
        return bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {return email; }

    public String getPhone() {
        return phone;
    }

    public BookingDates getBookingDates() {
        return bookingDates;
    }

    public boolean isDepositPaid() {
        return depositPaid;
    }
}

