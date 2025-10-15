package utils;

import io.restassured.response.Response;
import io.cucumber.datatable.DataTable;
import model.response.BookingResponse;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseValidator {

    public void validateSuccessfulPost(Response response, DataTable dataTable) {
        assertThat(response.getStatusCode()).isEqualTo(201);
        Map<String, String> data = dataTable.asMaps().get(0);
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        assertThat(bookingResponse.getBookingId()).isGreaterThan(0);
        if(data.get("roomId") != null) {
            assertThat(bookingResponse.getRoomId()).isEqualTo(Integer.parseInt(data.get("roomId")));
        }
        if(data.get("firstName") != null) {
            assertThat(bookingResponse.getFirstName()).isEqualTo(data.get("firstName"));
        }
        if(data.get("lastName") != null) {
            assertThat(bookingResponse.getLastName()).isEqualTo(data.get("lastName"));
        }
        if(data.get("email") != null) {
            assertThat(bookingResponse.getEmail()).isEqualTo(data.get("email"));
        }
        if(data.get("phone") != null) {
            assertThat(bookingResponse.getPhone()).isEqualTo(data.get("phone"));
        }
        if (data.get("depositPaid") != null) {
            assertThat(bookingResponse.isDepositPaid()).isEqualTo(Boolean.parseBoolean(data.get("depositPaid")));
        }
        if (data.get("checkIn") != null) {
            assertThat(bookingResponse.getBookingDates().getCheckIn()).isEqualTo(data.get("checkIn"));
        }
        if (data.get("checkOut") != null) {
            assertThat(bookingResponse.getBookingDates().getCheckOut()).isEqualTo(data.get("checkOut"));
        }
    }

    public static void validateErrorResponse400(Response response, DataTable dataTable) {
        Map<String, String> expectedResponseParams = dataTable.asMaps().get(0);
        List<String> actualErrors = response.jsonPath().getList("errors");
        String expectedError = expectedResponseParams.get("errors");
        assertThat(actualErrors)
                .as("Expected error message not found: " + expectedError)
                .contains(expectedError);
    }

}
