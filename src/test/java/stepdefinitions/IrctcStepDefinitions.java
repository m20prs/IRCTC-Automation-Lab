package stepdefinitions;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import steps.IrctcUserSteps;

public class IrctcStepDefinitions {

    @Steps
    IrctcUserSteps userSteps;

    @Given("Maddie is logged in with valid credentials")
    public void step_performLogin() {
        userSteps.executeLogin();
    }

    @Given("Maddie selects the first available train for {string}")
    public void step_setTravelClass(String travelClass) {
        userSteps.selectJourneyClass(travelClass);
    }
    
    // Maintain other unique step definitions here...
}