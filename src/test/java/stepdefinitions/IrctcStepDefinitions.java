package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import steps.IrctcUserSteps;

public class IrctcStepDefinitions {

    @Steps
    IrctcUserSteps userSteps;

    @Given("Maddie has initialized the IRCTC portal")
    public void step_initializePortal() {
        userSteps.initializeApp();
    }

    @Then("Maddie verifies the page title contains {string}")
    public void step_verifyTitle(String titlePart) {
        userSteps.verifyTitleContent(titlePart);
    }

    @Then("Maddie is logged in with valid credentials")
    public void step_performLogin() {
        userSteps.executeLogin();
    }

    @Then("Maddie should see the {string} link in the header")
    public void step_verifyHeaderLink(String linkText) {
        userSteps.validateDashboardAccess();
    }

    @Given("Maddie selects the station from {string} to {string}")
    public void step_selectRoute(String from, String to) {
        userSteps.setTravelRoute(from, to);
    }

    @Given("Maddie sets the travel date for {string}")
    public void step_setDate(String day) {
        userSteps.setDateToTomorrow();
    }

    @Given("the quota is selected as {string}")
    public void step_setQuota(String quota) {
        userSteps.selectJourneyQuota(quota);
    }

    @When("Maddie initiates the search at exactly {string} AM")
    public void step_timedSearch(String time) {
        userSteps.initiateSearchAtTime(time);
    }

    @When("Maddie selects the first available train for {string}")
    public void step_setTravelClass(String travelClass) {
        userSteps.selectJourneyClass(travelClass);
    }

    @When("Maddie provides passenger details for {string}")
    public void step_enterPassengerInfo(String name) {
        userSteps.enterDetailsAndSelectPayment(name);
    }

    @When("Maddie chooses {string} as the payment method")
    public void step_selectPayment(String method) {
        // Method covered in enterDetailsAndSelectPayment for efficiency
    }

    @Then("Maddie should be navigated to the payment gateway page")
    public void step_verifyPaymentPage() {
        org.hamcrest.MatcherAssert.assertThat(userSteps.getDriver().getCurrentUrl(), 
            org.hamcrest.Matchers.containsString("payment"));
    }
    
}