package steps;

import net.serenitybdd.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pages.IrctcLocatorsPage;
import stepdefinitions.BaseStepDefinition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IrctcUserSteps extends BaseStepDefinition {

    IrctcLocatorsPage irctcPage;

    @Step("Execute Login with Stealth and Manual Captcha")
    public void executeLogin() {
        retryAction("Login Sequence", () -> {
            irctcPage.loginLink.waitUntilVisible().click();
            irctcPage.usernameInput.type(username);
            irctcPage.passwordInput.type(decryptedPassword);
            
            System.out.println("[INFO] Manual CAPTCHA window open. Waiting " + getCaptchaWaitTime() + "s...");
            waitABit(getCaptchaWaitTime() * 1000L);
            
            irctcPage.signInButton.click();
        });
    }

    @Step("Dismiss IRCTC Page Overlays")
    public void dismissLocationPopup() {
        try {
            // Precise check for the 'OK' button that blocks the UI
            if (irctcPage.globalOkButton.withTimeoutOf(java.time.Duration.ofSeconds(5)).isVisible()) {
                irctcPage.globalOkButton.click();
            }
        } catch (Exception e) {
            System.out.println("[INFO] No blocking overlays found.");
        }
    }

    @Step("Inject Station via JavaScript: {0}")
    public void selectStation(WebElement element, String stationName) {
        // Professional approach: Bypass the clear() error by using JS to set value and trigger Angular input events
        evaluateJavascript(
            "arguments[0].value = '';" +
            "arguments[0].value = '" + stationName + "';" +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            element
        );
        waitABit(1000); 
        element.sendKeys(Keys.TAB); // Finalize selection
    }

    public void enterFromStation(String from) { selectStation(irctcPage.fromStationInput, from); }
    public void enterToStation(String to) { selectStation(irctcPage.toStationInput, to); }

    @Step("Set Dynamic Tatkal Journey Date")
    public void setTravelDate(String dateLabel) {
        String dateValue = "Tomorrow".equalsIgnoreCase(dateLabel) 
            ? LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
            : dateLabel;

        System.out.println("[STEP] Setting Date: " + dateValue);
        evaluateJavascript(
            "arguments[0].value = '" + dateValue + "';" +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
            irctcPage.datePickerInput
        );
    }

    @Step("Select Journey Quota: {0}")
    public void selectQuota(String quota) {
        irctcPage.quotaDropdown.click();
        // Use a more robust dynamic locator for the dropdown items
        String xpath = "//p-dropdownitem//li[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '" + quota.toUpperCase() + "')]";
        $(xpath).waitUntilClickable().click();
    }

    @Step("Select Class and Proceed to Booking: {0}")
    public void selectJourneyClass(String travelClass) {
        irctcPage.firstTrainResult.waitUntilVisible();
        // Professional dynamic Class selection
        String classXpath = "//div[contains(@class,'coach-type') and contains(.,'" + travelClass.toUpperCase() + "')]";
        $(classXpath).waitUntilClickable().click();
        
        waitABit(1000); // Wait for the 'Book Now' button to refresh for that class
        irctcPage.bookNowButton.waitUntilClickable().click();
    }

    @Step("Finalize Search")
    public void clickSearch() {
        irctcPage.searchButton.waitUntilClickable().click();
    }

    @Step("Verify Payment Gateway Navigation")
    public void verifyPaymentGatewayPage() {
        // Verify current URL or a specific payment header to confirm success
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("book-passenger"));
        System.out.println("[SUCCESS] Reached Passenger Details / Payment phase.");
    }
}