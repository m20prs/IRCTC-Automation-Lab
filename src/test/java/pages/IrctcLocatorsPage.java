package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;

public class IrctcLocatorsPage extends PageObject {

    // ── Global / Header ─────────────────────────────────────────────────────
    @FindBy(xpath = "//button[text()='OK']")
    public WebElementFacade globalOkButton;

    // ── Login ────────────────────────────────────────────────────────────────
    @FindBy(xpath = "//a[contains(text(),'LOGIN')]")
    public WebElementFacade loginLink;

    @FindBy(xpath = "//input[@formcontrolname='userid']")
    public WebElementFacade usernameInput;

    @FindBy(xpath = "//input[@formcontrolname='password']")
    public WebElementFacade passwordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'SIGN IN')]")
    public WebElementFacade signInButton;

    // ── Search Form ──────────────────────────────────────────────────────────
    @FindBy(xpath = "//input[@role='searchbox' and contains(@aria-controls,'pr_id_1')]")
    public WebElementFacade fromStationInput;

    @FindBy(xpath = "//input[@role='searchbox' and contains(@aria-controls,'pr_id_2')]")
    public WebElementFacade toStationInput;

    /** First autocomplete suggestion in ANY p-autocomplete dropdown */
    @FindBy(xpath = "(//p-autocompleteitem | //li[contains(@class,'ui-autocomplete-list-item')])[1]")
    public WebElementFacade firstAutoSuggestion;

    @FindBy(xpath = "//p-calendar[@formcontrolname='journeyDate']//input")
    public WebElementFacade datePickerInput;

    // ── Quota ────────────────────────────────────────────────────────────────
    /**
     * The IRCTC quota drop-down sits inside a p-dropdown whose label
     * shows the currently selected quota (e.g. "GENERAL", "TATKAL").
     */
    @FindBy(xpath = "//p-dropdown[@formcontrolname='journeyQuota']")
    public WebElementFacade quotaDropdown;

    /** Generic item in any open p-dropdown overlay */
    @FindBy(xpath = "//p-dropdownitem/li | //li[contains(@class,'ui-dropdown-item')]")
    public WebElementFacade quotaDropdownItem;

    // ── Search Button ────────────────────────────────────────────────────────
    @FindBy(xpath = "//button[@type='submit' and contains(.,'Search')]")
    public WebElementFacade searchButton;

    // ── Train List ───────────────────────────────────────────────────────────
    /** The first train card returned in results */
    @FindBy(xpath = "(//div[contains(@class,'train-info-main-block')])[1]")
    public WebElementFacade firstTrainResult;

    /**
     * Class / coach tab button inside a train card.
     * Usage: find all of these, then filter by text matching the desired class code.
     */
    @FindBy(xpath = "//div[contains(@class,'coach-type') or contains(@class,'class-type')]")
    public WebElementFacade classTab;

    /** "Book Now" button for the chosen class inside the first train card */
    @FindBy(xpath = "(//button[contains(text(),'Book Now') or contains(@class,'bookNow')])[1]")
    public WebElementFacade bookNowButton;

    // ── Passenger / Payment ──────────────────────────────────────────────────
    /** Appears on the payment gateway page to confirm navigation success */
    @FindBy(xpath = "//div[contains(@class,'payment-gateway') or contains(text(),'Payment') or contains(text(),'IRCTC iPay')]")
    public WebElementFacade paymentGatewayHeader;
}