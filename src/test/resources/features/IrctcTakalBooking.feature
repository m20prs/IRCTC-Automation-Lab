@irctc @tatkal @high-priority
Feature: IRCTC Tatkal Ticket Automation
  As a frequent traveler
  I want to automate the booking process for Tatkal tickets
  So that I can secure a seat within the first 60 seconds of the window opening

  Background:
    Given Maddie has initialized the IRCTC portal
    And Maddie closes the location pop-up if visible
    Then Maddie verifies the page title contains "IRCTC Next Generation"
    And Maddie is logged in with valid credentials

  @smoke @critical @tatkal_booking
  Scenario Outline: Book a Tatkal ticket for Sleeper Class
    Given Maddie selects the station from "<From>" to "<To>"
    And Maddie sets the travel date for "Tomorrow"
    And the quota is selected as "TATKAL"
    And Maddie selects the first available train for "<Class>"
    # When Maddie initiates the search at exactly "10:00:00" AM
    # And Maddie provides passenger details for "Maddie"
    # And Maddie chooses "UPI" as the payment method
    # Then Maddie should be navigated to the payment gateway page

    Examples:
      | From    | To        | Class |
      | CHENNAI | BANGALORE | SL    |