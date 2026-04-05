package stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.annotations.Steps;
import steps.IrctcUserSteps;

public class Hooks {

    @Steps
    IrctcUserSteps userSteps;

    @Before
    public void setupScenario() {
        // Initialize state via the steps library
        userSteps.initializeCredentials();
        userSteps.launchIrctc();
    }
}