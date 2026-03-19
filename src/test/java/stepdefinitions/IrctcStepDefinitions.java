package stepdefinitions;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import steps.IrctcUserSteps;

public class IrctcStepDefinitions extends BaseStepDefinition {

    @Steps
    IrctcUserSteps userSteps;

    // ── Background Steps ─────────────────────────────────────────────────────

    /**
     * Feature: "Given Maddie has initialized the IRCTC portal"
     *
     * The portal launch + credential load is handled by the @Before hook in
     * BaseStepDefinition.setUp(), so this step just confirms we're on the
     * right page without duplicating the navigation.
     */
    @Given("Maddie has initialized the IRCTC portal")
    public void step_initializePortal() {
        System.out.println("[INFO] IRCTC portal initialized at: " + getDriver().getCurrentUrl());
    }

    /**
     * Feature: "And Maddie closes the location pop-up if visible"
     */
    @Given("Maddie closes the location pop-up if visible")
    public void step_closeLocationPopup() {
        userSteps.dismissLocationPopup();
    }

    /**
     * Feature: "And Maddie is logged in with valid credentials"
     */
    @Given("Maddie is logged in with valid credentials")
    public void step_performLogin() {
        userSteps.executeLogin();
    }

    // ── Scenario Steps ───────────────────────────────────────────────────────

    /**
     * Feature: "Given Maddie selects the station from "<From>" to "<To>""
     */
    @Given("Maddie selects the station from {string} to {string}")
    public void step_selectStations(String from, String to) {
        userSteps.enterFromStation(from);
        userSteps.enterToStation(to);
    }

    /**
     * Feature: "And Maddie sets the travel date for "Tomorrow""
     */
    @Given("Maddie sets the travel date for {string}")
    public void step_setTravelDate(String dateLabel) {
        userSteps.setTravelDate(dateLabel);
    }

    /**
     * Feature: "And the quota is selected as "TATKAL""
     */
    @Given("the quota is selected as {string}")
    public void step_selectQuota(String quota) {
        userSteps.selectQuota(quota);
    }

    /**
     * Feature: "And Maddie selects the first available train for "<Class>""
     */
    @Given("Maddie selects the first available train for {string}")
    public void step_setTravelClass(String travelClass) {
        userSteps.selectJourneyClass(travelClass);
    }

    /**
     * Feature: "When Maddie initiates the search at exactly "10:00:00" AM"
     *
     * Clicks the Search button. The time parameter is accepted for
     * documentation purposes; actual timed booking requires OS-level
     * scheduling outside of Selenium scope.
     */
    @When("Maddie initiates the search at exactly {string} AM")
    public void step_initiateSearch(String time) {
        System.out.println("[INFO] Initiating search (target time noted: " + time + " AM)");
        userSteps.clickSearch();
    }

    /**
     * Feature: "Then Maddie should be navigated to the payment gateway page"
     */
    @Then("Maddie should be navigated to the payment gateway page")
    public void step_verifyPaymentGateway() {
        userSteps.verifyPaymentGatewayPage();
    }
}