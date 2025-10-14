package stepdefinitions;

import client.BookingClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.BookingDates;
import model.request.BookingRequest;
import model.response.BookingResponse;

import static org.assertj.core.api.Assertions.assertThat;


public class BookingSteps {

    private BookingRequest bookingRequest;
    private Response response;
    private final BookingClient bookingClient = new BookingClient();

    @Given("user wants to book a room")
    public void userWantsToBookARoom() {
        BookingDates bookingDates = new BookingDates("2025-10-21", "2025-10-22");
        bookingRequest = new BookingRequest("1", "James", "Smith",
                "james_smith@gmail.com", "049612234890", bookingDates);
    }

    @When("they submit a valid booking request")
    public void theySubmitAValidBookingRequest() {
        response = bookingClient.createBooking(bookingRequest);
    }

    @Then("the booking should be done successfully")
    public void theBookingShouldBeDoneSuccessfully() {
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(bookingRequest.getFirstname()).isEqualTo(bookingResponse.getFirstname());
    }
}
