package client;

import base.BaseApiClient;
import io.restassured.response.Response;
import model.request.BookingRequest;

public class BookingClient extends BaseApiClient {

    private final String BOOKING_ENDPOINT = "/booking";

    public Response createBooking(BookingRequest bookingRequest) {
        return post(BOOKING_ENDPOINT, bookingRequest);
    }

    public Response createBooking(String endpoint, BookingRequest bookingRequest) {
        return post(endpoint, bookingRequest);
    }
}
