@irctc @tatkal @tatkal_booking
Feature: IRCTC Tatkal Ticket Automation

  Background:
    Given Maddie has initialized the IRCTC portal
    And Maddie closes the location pop-up if visible
    And Maddie is logged in with valid credentials

  Scenario Outline: Book a Tatkal ticket for Sleeper Class
    Given Maddie selects the station from "<From>" to "<To>"
    And Maddie sets the travel date for "Tomorrow"
    And the quota is selected as "TATKAL"
    And Maddie selects the first available train for "<Class>"
    When Maddie initiates the search at exactly "10:00:00" AM
    Then Maddie should be navigated to the payment gateway page

    Examples:
      | From | To  | Class |
      | TMB  | MDU | SL    |