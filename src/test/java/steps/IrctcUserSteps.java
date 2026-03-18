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
        System.out.println("[STEP] Checking for IRCTC page overlays...");
        try {
            // Target the HTML 'OK' button that IRCTC shows on login
            String okButton = "//button[text()='OK']";
            if ($(okButton).isVisible()) {
                $(okButton).click();
                System.out.println("[INFO] Page overlay dismissed.");
            }
        } catch (Exception e) {
            System.out.println("[INFO] No page overlay found.");
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
        
        // MENTOR FIX: Add a small wait to ensure the login transition finishes
        System.out.println("[WAIT] Waiting for dashboard transition...");
        waitFor(3).seconds();
    }

    @Step("Validate successful redirection to account dashboard")
    public void validateDashboardAccess() {
        System.out.println("[STEP] Validating 'MY ACCOUNT' visibility on dashboard...");
        irctcPage.myAccountVerify.shouldBeVisible();
    }

    @Step("Enter travel route and select stations")
public void setTravelRoute(String from, String to) {
    // MENTOR FIX: Use sendKeys to avoid the framework's internal clear() command
    System.out.println("[STEP] Activating 'FROM' field and setting station: " + from);
    irctcPage.fromStationInput.waitUntilClickable().click();
    irctcPage.fromStationInput.sendKeys(from);
    waitFor(1).seconds(); // Wait for auto-suggest
    irctcPage.fromStationInput.sendKeys(Keys.TAB);
    
    System.out.println("[STEP] Activating 'TO' field and setting station: " + to);
    irctcPage.toStationInput.waitUntilClickable().click();
    irctcPage.toStationInput.sendKeys(to);
    waitFor(1).seconds(); // Wait for auto-suggest
    irctcPage.toStationInput.sendKeys(Keys.TAB);
}

    @Step("Set travel date for Tatkal (Tomorrow)")
    public void setTatkalDate() {
        // Dynamically calculate tomorrow's date
        String tomorrow = LocalDate.now().plusDays(1)
                                   .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("[STEP] Setting Tatkal journey date to: " + tomorrow);

        try {
            // Use JavaScript injection for maximum speed during Tatkal rush
            evaluateJavascript("arguments[0].value = '" + tomorrow + "';", irctcPage.datePickerInput);
            evaluateJavascript("arguments[0].dispatchEvent(new Event('change'))", irctcPage.datePickerInput);
            evaluateJavascript("arguments[0].dispatchEvent(new Event('input'))", irctcPage.datePickerInput);

            // Validate date was actually set
            waitFor(500).milliseconds();
            String actualValue = irctcPage.datePickerInput.getAttribute("value");
            assertThat("Date not set correctly! Expected: " + tomorrow + ", Got: " + actualValue,
                       actualValue, containsString(tomorrow));
            System.out.println("[SUCCESS] Date set and validated to: " + actualValue);
        } catch (Exception e) {
            System.out.println("[ERROR] Date setting via JavaScript failed: " + e.getMessage());
            System.out.println("[FALLBACK] Attempting date setting via UI interaction...");
            irctcPage.datePickerInput.click();
            irctcPage.datePickerInput.clear();
            irctcPage.datePickerInput.type(tomorrow);
        }
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
        LocalTime target = LocalTime.parse(targetTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Non-blocking wait instead of CPU-intensive busy-wait
        while (LocalTime.now().isBefore(target)) {
            try {
                Thread.sleep(100); // Check every 100ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[ERROR] Thread interrupted during Tatkal timing wait");
                break;
            }
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