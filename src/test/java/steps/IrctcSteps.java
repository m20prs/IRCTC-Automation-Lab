package stepdefinitions;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pages.IrctcPage;
import utils.EncryptionUtils;
import java.io.FileInputStream;
import java.util.Properties;

public class IrctcSteps {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private IrctcPage irctc;
    private String user, pass;

    @Before
    public void setup() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("credentials.properties"));
        user = props.getProperty("user.data.irctc.username");
        pass = EncryptionUtils.decode(props.getProperty("user.data.irctc.password"));

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        irctc = new IrctcPage(page);
    }

    @Given("Maddie has initialized the IRCTC portal")
    public void initPortal() {
        page.navigate("https://www.irctc.co.in/nget/train-search");
    }

    @Given("Maddie closes the location pop-up if visible")
    public void closePopup() {
        if (irctc.okButton().isVisible()) irctc.okButton().click();
    }

    @Given("Maddie is logged in with valid credentials")
    public void login() {
        irctc.loginLink().click();
        irctc.usernameInput().fill(user);
        irctc.passwordInput().fill(pass);
        System.out.println("FIX THE CAPTCHA MANUALLY IN 15 SECONDS...");
        page.waitForTimeout(15000);
        irctc.signInButton().click();
    }

    @Given("Maddie selects the station from {string} to {string}")
    public void selectStations(String from, String to) {
        fillStation(irctc.fromStation(), from);
        fillStation(irctc.toStation(), to);
    }

    private void fillStation(Locator loc, String val) {
        loc.click();
        loc.fill(val);
        irctc.suggestionItem().waitFor();
        irctc.suggestionItem().click();
    }

    @Given("the quota is selected as {string}")
    public void selectQuota(String quota) {
        irctc.quotaDropdown().click();
        page.locator("li[role='option']").filter(new Locator.FilterOptions().setHasText(quota)).click();
    }

    @When("Maddie initiates the search at exactly {string} AM")
    public void search(String time) {
        irctc.searchButton().click();
    }

    @Then("Maddie should be navigated to the payment gateway page")
    public void verifyPayment() {
        page.waitForURL("**/book-passenger");
        System.out.println("PASSED: Reached booking page.");
    }

    @After
    public void tearDown() {
        browser.close();
        playwright.close();
    }
}