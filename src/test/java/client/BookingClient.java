package client;

import base.BaseApiClient;
import io.restassured.response.Response;
import model.request.BookingRequest;

public class BookingClient extends BaseApiClient {

    private final String BOOKING_ENDPOINT = "/booking";

    public Response createBooking(BookingRequest bookingRequest) {
        return post(BOOKING_ENDPOINT, bookingRequest);
    }
    public Response getBooking(int bookingId) {
        return get(BOOKING_ENDPOINT + "/" + bookingId);
    }

    public Response updateBooking(int bookingId, BookingRequest bookingRequest) {
        return put(BOOKING_ENDPOINT  + "/" + bookingId, bookingRequest);
    }

    public Response deleteBooking(int bookingId) {
        return delete(BOOKING_ENDPOINT + "/" + bookingId);
    }
}
