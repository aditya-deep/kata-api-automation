package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class BookingSteps {
    Map<String, Object> bookingPayload;
    Response response;
    Logger logger = Logger.getLogger(BookingSteps.class.getName());

    @Given("user wants to book a room")
    public void user_wants_to_book_a_room() {
        bookingPayload = new HashMap<>();
        bookingPayload.put("roomid", 1);
        bookingPayload.put("firstname", "firstName");
        bookingPayload.put("lastname", "lastName");
        bookingPayload.put("email", "test_booking@gmail.com");
        bookingPayload.put("phone", "049612345678");

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-10-15");
        bookingDates.put("checkout", "2025-10-17");

        bookingPayload.put("bookingdates", bookingDates);
        logger.info(bookingPayload.toString());
    }

    @When("they submit a valid booking request")
    public void they_submit_a_valid_booking_request() {
        response = given()
                .baseUri("https://automationintesting.online")
                .basePath("/api/booking")
                .contentType(ContentType.JSON)
                .body(bookingPayload)
                .when()
                .post();
    }

    @Then("the booking should be done successfully")
    public void the_booking_should_be_done_successfully() {
        logger.info(response.asString());
        logger.info(response.getBody().asString());
        logger.info(String.valueOf(response.statusCode()));
        int bookingId = response.jsonPath().getInt("bookingid");
        assertThat(bookingId, notNullValue());
    }
}
