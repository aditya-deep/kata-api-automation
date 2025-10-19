Feature: Booking API tests

  @positive
  Scenario Outline: Successful room booking with only mandatory fields
    Given user wants to book a room with mandatory details
      | roomId   | firstName   | lastName   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <checkIn> | <checkOut> |
    When they submit a valid booking request
    Then the booking should be done successfully with status code 201
    And the response should contain booking details
      | roomId   | firstName   | lastName   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <checkIn> | <checkOut> |
    Examples:
      | roomId | firstName | lastName | checkIn    | checkOut   |
      | 1      | Jon       | Smith    | 2025-10-27 | 2025-10-28 |
      | 2      | Alice     | Lee      | 2025-10-24 | 2025-10-25 |

  @positive
  Scenario Outline: Successful room booking with all fields
    Given user wants to book a room with following details
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> |
    When they submit a valid booking request
    Then the booking should be done successfully with status code 201
    And the response should contain booking details
      | roomId   | firstName   | lastName   | checkIn   | checkOut   | depositPaid |
      | <roomId> | <firstName> | <lastName> | <checkIn> | <checkOut> | false       |
    Examples:
      | roomId | firstName | lastName | email                | phone        | checkIn    | checkOut   |
      | 3      | John      | Wick     | john_wick@gmail.com  | 049612234891 | 2025-10-26 | 2025-10-27 |
      | 1      | Sarah     | Wick     | sarah_wick@gmail.com | +49612234892 | 2025-10-30 | 2025-10-31 |

  @negative
  Scenario Outline: User tries to book with missing dates, past dates and invalid date format
    Given user wants to book a room with following details
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> |
    When user submits a booking request
    Then the booking request should fail with status code <statusCode>
    And the error response should contain following error messages
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   | errors   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> | <errors> |
    Examples:
      | roomId | firstName | lastName | email                 | phone        | checkIn    | checkOut   | statusCode | errors                            |
      | 1      | James     | Smith    | james_smith@gmail.com | 049612234890 |            |            | 400        | must not be null,must not be null |
      | 1      | James     | Smith    | james_smith@gmail.com | 049612234890 |            | 2025-10-20 | 400        | must not be null                  |
      | 1      | James     | Smith    | james_smith@gmail.com | 049612234890 | 2025-10-20 |            | 400        | must not be null                  |
      | 1      | James     | Smith    | james_smith@gmail.com | 049612234890 | 2025/10/26 | 2025/10/28 | 400        | Failed to create booking          |

  @negative
  Scenario Outline: User tries to book with invalid room id
    Given user wants to book a room with following details
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> |
    When user submits a booking request
    Then the booking request should fail with status code <statusCode>
    And the error response should contain following error messages
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   | errors   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> | <errors> |
    Examples:
      | roomId | firstName | lastName | email           | phone        | checkIn    | checkOut   | statusCode | errors                             |
      | 0      | James     | Smith    | james@gmail.com | 049612234890 | 2025-10-14 | 2025-10-15 | 400        | must be greater than or equal to 1 |
      | -10    | James     | Smith    | james@gmail.com | 049612234890 | 2025-10-14 | 2025-10-15 | 400        | must be greater than or equal to 1 |
      | 1.2    | James     | Smith    | james@gmail.com | 049612234890 | 2025-10-14 | 2025-10-15 | 400        | Failed to create booking           |
      |        | James     | Smith    | james@gmail.com | 049612234890 | 2025-10-14 | 2025-10-15 | 400        | must be greater than or equal to 1 |

  @negative
  Scenario Outline: User tries to book with invalid values for firstname and lastname
    Given user wants to book a room with following details
      | roomId   | firstName   | lastName   | checkIn   | checkOut   | email   | phone   |
      | <roomId> | <firstName> | <lastName> | <checkIn> | <checkOut> | <email> | <phone> |
    When user submits a booking request
    Then the booking request should fail with status code <statusCode>
    And the error response should contain following error messages
      | roomId   | firstName   | lastName   | checkIn   | checkOut   | errors   |
      | <roomId> | <firstName> | <lastName> | <checkIn> | <checkOut> | <errors> |
    Examples:
      | roomId | firstName           | lastName                        | checkIn    | checkOut   | email           | phone        | statusCode | errors                        |
      | 2      |                     | Smith                           | 2025-10-24 | 2025-10-25 | james@gmail.com | 049612234890 | 400        | Firstname should not be blank |
      | 2      | Ja                  | Smith                           | 2025-10-24 | 2025-10-25 | james@gmail.com | 049612234890 | 400        | size must be between 3 and 18 |
      | 2      | JamesSmithJamesS_*& | Smith                           | 2025-10-24 | 2025-10-25 | james@gmail.com | 049612234890 | 400        | size must be between 3 and 18 |
      | 2      | James               |                                 | 2025-10-24 | 2025-10-25 | james@gmail.com | 049612234890 | 400        | Lastname should not be blank  |
      | 2      | James               | Sm                              | 2025-10-24 | 2025-10-25 | james@gmail.com | 049612234890 | 400        | size must be between 3 and 30 |
      | 2      | James               | JamesSmithJamesS_*&JamesSmith-! | 2025-10-24 | 2025-10-25 | james@gmail.com | 049612234890 | 400        | size must be between 3 and 30 |

  @negative
  Scenario Outline: User tries to book with invalid email
    Given user wants to book a room with following details
      | roomId   | firstName   | lastName   | email   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <email> | <checkIn> | <checkOut> |
    When user submits a booking request
    Then the booking request should fail with status code <statusCode>
    And the error response should contain following error messages
      | roomId   | firstName   | lastName   | email   | checkIn   | checkOut   | errors   |
      | <roomId> | <firstName> | <lastName> | <email> | <checkIn> | <checkOut> | <errors> |
    Examples:
      | roomId | firstName | lastName | email                  | checkIn    | checkOut   | statusCode | errors                              |
      | 1      | Lee       | Kim      | invalid-email          | 2025-10-14 | 2025-10-25 | 400        | must be a well-formed email address |
      | 1      | James     | Smith    | james_smith@@gmail.com | 2025-10-16 | 2025-10-25 | 400        | must be a well-formed email address |

  @negative
  Scenario Outline: User tries to book with invalid phone number
    Given user wants to book a room with following details
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> |
    When user submits a booking request
    Then the booking request should fail with status code <statusCode>
    And the error response should contain following error messages
      | roomId   | firstName   | lastName   | email   | phone   | checkIn   | checkOut   | errors   |
      | <roomId> | <firstName> | <lastName> | <email> | <phone> | <checkIn> | <checkOut> | <errors> |
    Examples:
      | roomId | firstName            | lastName | phone                  | checkIn    | checkOut   | statusCode | errors                         |
      | 1      | JamesSmithJamesSmith | Smith    |                        | 2025-10-20 | 2025-10-21 | 400        | size must be between 3 and 18  |
      | 1      | JamesSmithJamesSmith | Smith    | 0496123489             | 2025-10-20 | 2025-10-21 | 400        | size must be between 11 and 21 |
      | 1      | JamesSmithJamesSmith | Smith    | 0496123489049612348912 | 2025-10-20 | 2025-10-21 | 400        | size must be between 11 and 21 |

  @negative
  Scenario: User get 404 Not Found when accessing invalid API endpoint
    Given user wants to book a room with following details
      | roomId | firstName | lastName | email                 | phone        | checkIn    | checkOut   |
      | 3      | Kim       | Smith    | james_smith@gmail.com | 049612234890 | 2025-10-29 | 2025-10-30 |
    When user submits a booking request to an invalid endpoint "/bookings"
    Then the booking request should fail with status code 404
