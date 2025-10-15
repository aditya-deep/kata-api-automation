package utils;

import io.restassured.response.Response;
import io.cucumber.datatable.DataTable;
import model.response.BookingResponse;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseValidator {

    private String roomId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String depositPaid;
    private String checkIn;
    private String checkOut;


    public void validateSuccessfulPost(Response response, DataTable dataTable) {
        assertThat(response.getStatusCode()).isEqualTo(201);
        mapFields(dataTable);
        BookingResponse bookingResponse = response.as(BookingResponse.class);
        assertThat(bookingResponse.getBookingId()).isNotNull().isGreaterThan(0);
        if(roomId != null) {
            assertThat(bookingResponse.getRoomId()).isEqualTo(Integer.parseInt(roomId));
        }
        if(firstName != null) {
            assertThat(bookingResponse.getFirstName()).isEqualTo(firstName);
        }
        if(lastName != null) {
            assertThat(bookingResponse.getLastName()).isEqualTo(lastName);
        }
        if(email != null) {
            assertThat(bookingResponse.getEmail()).isEqualTo(email);
        }
        if(phone != null) {
            assertThat(bookingResponse.getPhone()).isEqualTo(phone);
        }
        if (depositPaid != null) {
            assertThat(bookingResponse.isDepositPaid()).isEqualTo(Boolean.parseBoolean(depositPaid));
        }
        if (checkIn != null) {
            assertThat(bookingResponse.getBookingDates().getCheckIn()).isEqualTo(checkIn);
        }
        if (checkOut != null) {
            assertThat(bookingResponse.getBookingDates().getCheckOut()).isEqualTo(checkOut);
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

    public void mapFields(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.roomId = data.get("roomId");
        this.firstName = data.get("firstName");
        this.lastName = data.get("lastName");
        this.email = data.get("email");
        this.phone = data.get("phone");
        this.depositPaid = data.get("depositPaid");
        this.checkIn = data.get("checkIn");
        this.checkOut = data.get("checkOut");
    }

}
