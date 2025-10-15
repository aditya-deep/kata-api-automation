Feature: Booking API tests

  @positive
  Scenario Outline: Successful room booking
    Given user wants to book a room with following details
      | roomId    | firstName    | lastName   | email   | phone   | checkIn     | checkOut    |
      | <roomId>  | <firstName>  | <lastName> | <email> | <phone> | <checkIn>   | <checkOut>  |
    When they submit a valid booking request
    Then the booking should be done successfully with status code 201
    And the response should contain booking details
      | roomId    | firstName    | lastName   | checkIn     | checkOut    | depositPaid |
      | <roomId>  | <firstName>  | <lastName> | <checkIn>   | <checkOut>  | false       |
    Examples:
      | roomId | firstName | lastName | email                 | phone         | checkIn     | checkOut    |
      | 1      | James     | Smith    | james_smith@gmail.com | 049612234890  | 2025-10-20  | 2025-10-21  |
      | 2      | Alice     | Brown    | alice@gmail.com       | 048712345678  | 2025-10-21  | 2025-10-22  |

  @negative
  Scenario Outline: Booking with invalid room id
    Given user tries to book a room with invalid input data
      | roomId    | firstName    | lastName   | email   | phone   | checkIn     | checkOut    |
      | <roomId>  | <firstName>  | <lastName> | <email> | <phone> | <checkIn>   | <checkOut>  |
    When user submits a booking request
    Then the booking request should fail with status code <statusCode>
    And the error response should contain appropriate error messages
      | roomId    | firstName    | lastName   | email   | phone   | checkIn     | checkOut    | errors    |
      | <roomId>  | <firstName>  | <lastName> | <email> | <phone> | <checkIn>   | <checkOut>  | <errors>  |
    Examples:
      | roomId | firstName | lastName | email               | phone         | checkIn     | checkOut    | statusCode | errors                                     |
      | 0      | James     | Smith    | james@gmail.com     | 012345678901  | 2025-10-14  | 2025-10-15  | 400        |   must be greater than or equal to 1       |
      | 1      |           | Smith    | james@gmail.com     | 012345678912  | 2025-10-14  | 2025-10-15  | 400        |   Firstname should not be blank            |
      | 1      | James     | Smith    | invalid-email       | 012345678912  | 2025-10-14  | 2025-10-15  | 400        |   must be a well-formed email address      |
      | 1      | James     | Smith    | james@gmail.com     |               | 2025-10-14  | 2025-10-15  | 400        |   Failed to create booking                 |
      | 1      | James     | Smith    | james@gmail.com     | 0123456789    | 2025-10-16  | 2025-10-15  | 400        |   size must be between 11 and 21           |