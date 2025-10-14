package model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.BookingDates;

public class BookingResponse {
    @JsonProperty(required = true)
    private int bookingid;
    private String roomid;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private boolean depositpaid;
    private BookingDates bookingdates;

    public BookingResponse() {}

    public BookingResponse(int bookingid, String roomid, String firstname,
                           String lastname, String email,
                           String phone, boolean depositpaid, BookingDates bookingdates) {
        this.bookingid = bookingid;
        this.roomid = roomid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
    }

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }
}

