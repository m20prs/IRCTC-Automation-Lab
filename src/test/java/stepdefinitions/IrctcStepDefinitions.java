package stepdefinitions;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import steps.IrctcUserSteps;

/**
 * Cucumber step-definition class — plain class, no extends.
 *
 * Cucumber forbids step-definition classes from extending a class that owns
 * @Before/@After. The fix is simple: don't extend BaseStepDefinition here.
 * All shared behaviour is accessed through the @Steps-injected IrctcUserSteps,
 * which extends BaseStepDefinition as a Serenity Steps class — Cucumber never
 * inspects its parent, so there is no conflict.
 */
public class IrctcStepDefinitions {

    @Steps
    IrctcUserSteps userSteps;

    // ── Background ────────────────────────────────────────────────────────────

    @Given("Maddie has initialized the IRCTC portal")
    public void step_initializePortal() {
        // Browser launch + credential load is handled by BaseStepDefinition's @Before.
        System.out.println("[INFO] IRCTC portal ready.");
    }

    @Given("Maddie closes the location pop-up if visible")
    public void step_closeLocationPopup() {
        userSteps.dismissLocationPopup();
    }

    @Given("Maddie is logged in with valid credentials")
    public void step_performLogin() {
        userSteps.executeLogin();
    }

    // ── Scenario Steps ────────────────────────────────────────────────────────

    @Given("Maddie selects the station from {string} to {string}")
    public void step_selectStations(String from, String to) {
        userSteps.enterFromStation(from);
        userSteps.enterToStation(to);
    }

    @Given("Maddie sets the travel date for {string}")
    public void step_setTravelDate(String dateLabel) {
        userSteps.setTravelDate(dateLabel);
    }

    @Given("the quota is selected as {string}")
    public void step_selectQuota(String quota) {
        userSteps.selectQuota(quota);
    }

    @Given("Maddie selects the first available train for {string}")
    public void step_setTravelClass(String travelClass) {
        userSteps.selectJourneyClass(travelClass);
    }

    @When("Maddie initiates the search at exactly {string} AM")
    public void step_initiateSearch(String time) {
        System.out.println("[INFO] Initiating search (target time noted: " + time + " AM)");
        userSteps.clickSearch();
    }

    @Then("Maddie should be navigated to the payment gateway page")
    public void step_verifyPaymentGateway() {
        userSteps.verifyPaymentGatewayPage();
    }
}