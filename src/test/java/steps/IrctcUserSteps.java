package steps;

import pages.IrctcLocatorsPage;
import stepdefinitions.BaseStepDefinition;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class IrctcUserSteps extends BaseStepDefinition {

    IrctcLocatorsPage irctcPage;

    @Step("Initialize application and load credentials")
    public void initializeApp() {
        System.out.println("[STEP] Initializing IRCTC Application and loading user credentials...");
        launchIrctc(); 
        initializeCredentials(); 
    }

    @Step("Dismiss location or notification alert")
    public void dismissLocationPopup() {
        System.out.println("[STEP] Checking for location/notification alerts...");
        try {
            getAlert().accept();
            System.out.println("[INFO] Browser alert accepted.");
        } catch (Exception e) {
            System.out.println("[INFO] No browser alert found or already dismissed.");
        }
    }

    @Step("Check if page title contains: {0}")
    public void verifyTitleContent(String expectedText) {
        System.out.println("[STEP] Verifying page title contains: " + expectedText);
        assertThat("Title mismatch!", getDriver().getTitle(), containsString(expectedText));
    }

    @Step("Execute login sequence")
    public void executeLogin() {
        System.out.println("[STEP] Clicking Login/Register button...");
        irctcPage.loginButton.click();
        
        System.out.println("[STEP] Entering username: " + username);
        irctcPage.usernameInput.type(username);
        
        System.out.println("[STEP] Entering decrypted password...");
        irctcPage.passwordInput.type(decryptedPassword);
        
        System.out.println("[WAIT] Waiting 10 seconds for manual Captcha entry...");
        waitFor(10).seconds();
        
        System.out.println("[STEP] Clicking SIGN IN...");
        irctcPage.signInButton.click();
    }

    @Step("Validate successful redirection to account dashboard")
    public void validateDashboardAccess() {
        System.out.println("[STEP] Validating 'MY ACCOUNT' visibility on dashboard...");
        irctcPage.myAccountVerify.shouldBeVisible();
    }

    @Step("Enter travel route and select stations")
    public void setTravelRoute(String from, String to) {
        System.out.println("[STEP] Setting 'FROM' station: " + from);
        irctcPage.fromStationInput.type(from);
        irctcPage.fromStationInput.sendKeys(Keys.TAB);
        
        System.out.println("[STEP] Setting 'TO' station: " + to);
        irctcPage.toStationInput.type(to);
        irctcPage.toStationInput.sendKeys(Keys.TAB);
    }

    @Step("Set travel date to tomorrow")
    public void setDateToTomorrow() {
        String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println("[STEP] Setting travel date to tomorrow: " + tomorrow);
        irctcPage.datePickerInput.typeAndEnter(tomorrow);
    }

    @Step("Select journey quota: {0}")
    public void selectJourneyQuota(String quota) {
        System.out.println("[STEP] Selecting journey quota: " + quota);
        irctcPage.quotaDropdown.click();
        irctcPage.quotaDropdown.selectByVisibleText(quota);
    }

    @Step("Initiate search at precision time: {0}")
    public void initiateSearchAtTime(String targetTime) {
        System.out.println("[WAIT] Waiting for precision Tatkal time: " + targetTime);
        while (!LocalTime.now().toString().contains(targetTime)) {
            // Precise busy-wait
        }
        System.out.println("[STEP] Time reached! Clicking Search button at " + LocalTime.now());
        irctcPage.searchButton.click();
    }

    @Step("Select journey class: {0}")
    public void selectJourneyClass(String travelClass) {
        System.out.println("[STEP] Selecting journey class: " + travelClass);
        irctcPage.classDropdown.selectByVisibleText(travelClass);
    }

    @Step("Enter passenger and payment details")
    public void enterDetailsAndSelectPayment(String name) {
        System.out.println("[STEP] Entering passenger name: " + name);
        irctcPage.passengerNameInput.type(name);
        
        System.out.println("[STEP] Selecting UPI as payment method...");
        irctcPage.upiPaymentOption.click();
    }
}