package stepdefinitions;

import client.BookingClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.BookingDates;
import model.request.BookingRequest;
import utils.ResponseValidator;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.ResponseValidator.*;


public class BookingSteps {

    private BookingRequest bookingRequest;
    private Response response;
    private final BookingClient bookingClient = new BookingClient();
    ResponseValidator responseValidator = new ResponseValidator();

    @Given("user wants to book a room with following details")
    public void userWantsToBookARoom(DataTable dataTable) {
        bookingRequest = prepareBookingRequest(dataTable);
    }

    @Given("user wants to book a room with mandatory details")
    public void userWantsToBookARoomWithMandatoryDetails(DataTable dataTable) {
        bookingRequest = prepareBookingRequestWithMandatoryFields(dataTable);
    }

    @When("they submit a valid booking request")
    public void theySubmitAValidBookingRequest() {
        response = bookingClient.createBooking(bookingRequest);
    }

    @Then("the booking should be done successfully with status code {int}")
    public void theBookingShouldBeDoneSuccessfully(int expectedStatusCode) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatusCode);
    }

    @And("the response should contain booking details")
    public void theResponseShouldContainBookingDetails(DataTable dataTable) {
        responseValidator.validateSuccessfulPost(response, dataTable);
    }

    @When("user submits a booking request")
    public void userSubmitsABookingRequest() {
        response = bookingClient.createBooking(bookingRequest);
    }

    @When("user submits a booking request to an invalid endpoint {string}")
    public void userSubmitsABookingRequestToInvalidEndpoint(String endPoint) {
        response = bookingClient.createBooking(endPoint, bookingRequest);
    }

    @Then("the booking request should fail with status code {int}")
    public void userShouldReceiveAErrorResponse(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }

    @And("the error response should contain following error messages")
    public void theErrorResponseShouldContainAppropriateErrorMessages(DataTable dataTable) {
        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 400:
                validateErrorResponse400(response, dataTable);
                break;
            case 409:
                validateErrorResponse409(response, dataTable);
                break;
            default:
                throw new IllegalArgumentException("Unsupported status code for error validation: " + statusCode);
        }
    }

    //reusable method to prepare booking request
    private BookingRequest prepareBookingRequest(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckIn(getValue(data, "checkIn"));
        bookingDates.setCheckOut(getValue(data, "checkOut"));

        BookingRequest bookingRequest = new BookingRequest();

        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setRoomId(getValue(data, "roomId"));
        bookingRequest.setFirstName(getValue(data, "firstName"));
        bookingRequest.setLastName(getValue(data, "lastName"));
        bookingRequest.setEmail(getValue(data, "email"));
        bookingRequest.setPhone(getValue(data, "phone"));
        return bookingRequest;
    }

    private BookingRequest prepareBookingRequestWithMandatoryFields(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckIn(getValue(data, "checkIn"));
        bookingDates.setCheckOut(getValue(data, "checkOut"));

        BookingRequest bookingRequest = new BookingRequest();

        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setRoomId(getValue(data, "roomId"));
        bookingRequest.setFirstName(getValue(data, "firstName"));
        bookingRequest.setLastName(getValue(data, "lastName"));

        return bookingRequest;
    }

    private String getValue(Map<String, String> data, String key) {
        String value = data.get(key);
        if (value == null || value.trim().equalsIgnoreCase("empty")) {
            return "";
        }
        return value.trim();
    }
}
