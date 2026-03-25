@irctc @tatkal @tatkal_booking
Feature: IRCTC Tatkal Ticket Automation
  As a Frequent Traveler, 
  I want to automate the booking sequence
  So that I can bypass UI delays during high-concurrency windows.

  Background:
    Given Maddie has initialized the IRCTC portal
    And Maddie closes the location pop-up if visible
    And Maddie is logged in with valid credentials

  @smoke @critical
  Scenario Outline: Automate Tatkal Booking Flow
    Given Maddie selects the station from "<From>" to "<To>"
    And Maddie sets the travel date for "Tomorrow"
    And the quota is selected as "TATKAL"
    And Maddie selects the first available train for "<Class>"
    When Maddie initiates the search at exactly "11:00:00" AM
    Then Maddie should be navigated to the payment gateway page

    Examples:
      | From    | To        | Class |
      | CHENNAI | BANGALORE | SL    |