package steps;

import pages.IrctcLocatorsPage;
import stepdefinitions.BaseStepDefinition;
import net.serenitybdd.annotations.Step;

public class IrctcUserSteps extends BaseStepDefinition {
    IrctcLocatorsPage irctcPage;

    @Step("Execute login sequence")
    public void executeLogin() {
        irctcPage.loginLink.waitUntilVisible();
        evaluateJavascript("arguments[0].click();", irctcPage.loginLink);
        
        irctcPage.usernameInput.type(username);
        irctcPage.passwordInput.type(decryptedPassword);
        
        waitFor(12).seconds(); // Manual Captcha entry
        irctcPage.signInButton.click();
    }

    @Step("Dismiss location or notification alert")
    public void dismissLocationPopup() {
        if (irctcPage.globalOkButton.isCurrentlyVisible()) {
            irctcPage.globalOkButton.click();
        }
    }
}