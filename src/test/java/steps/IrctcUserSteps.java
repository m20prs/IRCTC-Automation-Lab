package steps;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
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

    // ── Login ─────────────────────────────────────────────────────────────────

    @Step("Click LOGIN link and enter credentials, then wait for manual CAPTCHA")
    public void executeLogin() {
        retryAction("Click login link", () -> {
            irctcPage.loginLink.waitUntilVisible();
            evaluateJavascript("arguments[0].click();", irctcPage.loginLink);
        });

        irctcPage.usernameInput.waitUntilVisible();
        irctcPage.usernameInput.type(username);
        irctcPage.passwordInput.type(decryptedPassword);

        // Wait for manual CAPTCHA entry (configurable via serenity.conf: captcha.wait.seconds)
        int waitSeconds = getCaptchaWaitTime();
        System.out.println("[INFO] Waiting " + waitSeconds + " seconds for manual CAPTCHA entry...");
        waitABit(waitSeconds * 1000L);

        irctcPage.signInButton.waitUntilClickable();
        irctcPage.signInButton.click();
    }

    // ── Popup ─────────────────────────────────────────────────────────────────

    @Step("Dismiss location or notification alert if present")
    public void dismissLocationPopup() {
        try {
            if (irctcPage.globalOkButton.isCurrentlyVisible()) {
                irctcPage.globalOkButton.click();
            }
        } catch (Exception e) {
            System.out.println("[INFO] No location/notification popup detected.");
        }
    }

    // ── Station Selection ─────────────────────────────────────────────────────

    @Step("Enter From station: {0}")
    public void enterFromStation(String fromStation) {
        retryAction("Enter FROM station", () -> {
            irctcPage.fromStationInput.waitUntilVisible();
            irctcPage.fromStationInput.clear();
            irctcPage.fromStationInput.type(fromStation);
            waitABit(1500);
            pickFirstAutoSuggestion();
        });
    }

    @Step("Enter To station: {0}")
    public void enterToStation(String toStation) {
        retryAction("Enter TO station", () -> {
            irctcPage.toStationInput.waitUntilVisible();
            irctcPage.toStationInput.clear();
            irctcPage.toStationInput.type(toStation);
            waitABit(1500);
            pickFirstAutoSuggestion();
        });
    }

    private void pickFirstAutoSuggestion() {
        // Prefer clicking the Serenity facade; fall back to keyboard navigation
        try {
            irctcPage.firstAutoSuggestion.waitUntilVisible();
            irctcPage.firstAutoSuggestion.click();
        } catch (Exception e) {
            getDriver().switchTo().activeElement().sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        }
    }

    // ── Date Selection ────────────────────────────────────────────────────────

    @Step("Set travel date to: {0}")
    public void setTravelDate(String dateLabel) {
        String dateValue;
        if ("Tomorrow".equalsIgnoreCase(dateLabel)) {
            dateValue = LocalDate.now()
                    .plusDays(1)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            dateValue = dateLabel;
        }

        retryAction("Set travel date", () -> {
            irctcPage.datePickerInput.waitUntilVisible();
            // Clear any existing value and type the new date
            irctcPage.datePickerInput.clear();
            irctcPage.datePickerInput.type(dateValue);
            // Close calendar overlay if open
            irctcPage.datePickerInput.sendKeys(Keys.ESCAPE);
        });
    }

    // ── Quota Selection ───────────────────────────────────────────────────────

    @Step("Select quota: {0}")
    public void selectQuota(String quota) {
        retryAction("Select quota: " + quota, () -> {
            irctcPage.quotaDropdown.waitUntilClickable();
            irctcPage.quotaDropdown.click();
            waitABit(500);

            // Find the dropdown item whose text matches the requested quota
            List<WebElement> items = getDriver()
                    .findElements(By.xpath("//p-dropdownitem//li | //li[contains(@class,'ui-dropdown-item')]"));
            for (WebElement item : items) {
                if (item.getText().trim().equalsIgnoreCase(quota)) {
                    item.click();
                    return;
                }
            }
            throw new RuntimeException("Quota option not found: " + quota);
        });
    }

    // ── Train & Class Selection ───────────────────────────────────────────────

    @Step("Select travel class: {0}")
    public void selectJourneyClass(String travelClass) {
        retryAction("Select class: " + travelClass, () -> {
            // Wait for at least one train result to appear
            irctcPage.firstTrainResult.waitUntilVisible();
            waitABit(1000);

            // Find the class tab matching the requested class code (e.g. "SL")
            List<WebElement> classTabs = getDriver().findElements(
                    By.xpath("//div[contains(@class,'coach-type') or contains(@class,'class-type')]"));

            boolean found = false;
            for (WebElement tab : classTabs) {
                if (tab.getText().trim().toUpperCase().contains(travelClass.toUpperCase())) {
                    tab.click();
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new RuntimeException("Travel class tab not found: " + travelClass);
            }

            waitABit(500);

            // Click Book Now for the selected class
            irctcPage.bookNowButton.waitUntilClickable();
            irctcPage.bookNowButton.click();
        });
    }

    // ── Search ────────────────────────────────────────────────────────────────

    @Step("Click Search button")
    public void clickSearch() {
        retryAction("Click Search button", () -> {
            irctcPage.searchButton.waitUntilClickable();
            irctcPage.searchButton.click();
        });
    }

    // ── Payment Verification ──────────────────────────────────────────────────

    @Step("Verify navigation to Payment Gateway page")
    public void verifyPaymentGatewayPage() {
        irctcPage.paymentGatewayHeader.waitUntilVisible();
        System.out.println("[INFO] Successfully navigated to the Payment Gateway page.");
    }
}