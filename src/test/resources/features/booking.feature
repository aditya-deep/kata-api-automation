Feature: Booking API tests

  Scenario: Successful room booking
    Given user wants to book a room
    When they submit a valid booking request
    Then the booking should be done successfully


