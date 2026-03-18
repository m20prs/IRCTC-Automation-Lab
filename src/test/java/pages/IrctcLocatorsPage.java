package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;

public class IrctcLocatorsPage extends PageObject {

    // --- Login Section ---
    @FindBy(xpath = "//button[contains(text(), 'LOGIN') and contains(text(), 'REGISTER')]")
    public WebElementFacade loginButton;

    @FindBy(xpath = "//input[@placeholder='User Name']")
    public WebElementFacade usernameInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    public WebElementFacade passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'SIGN IN')]")
    public WebElementFacade signInButton;

    @FindBy(xpath = "//span[contains(text(), 'MY ACCOUNT')] | //button[contains(text(), 'MY ACCOUNT')]")
    public WebElementFacade myAccountVerify;

    // --- Search Section ---
    // More resilient locators using formcontrolname attribute
    @FindBy(xpath = "//input[@formcontrolname='origin' or @formcontrolname='from']")
    public WebElementFacade fromStationInput;

    @FindBy(xpath = "//input[@formcontrolname='destination' or @formcontrolname='to']")
    public WebElementFacade toStationInput;

    @FindBy(xpath = "//input[@formcontrolname='journeyDate' or @formcontrolname='dateOfJourney']")
    public WebElementFacade datePickerInput;

    @FindBy(xpath = "//select[@formcontrolname='journeyQuota' or @formcontrolname='quota'] | //*[@formcontrolname='journeyQuota' or @formcontrolname='quota']")
    public WebElementFacade quotaDropdown;

    @FindBy(xpath = "//select[@formcontrolname='journeyClass' or @formcontrolname='class'] | //*[@formcontrolname='journeyClass' or @formcontrolname='class']")
    public WebElementFacade classDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Search')] | //button[@type='submit'][contains(., 'Search')]")
    public WebElementFacade searchButton;

    // --- Passenger & Payment Section ---
    @FindBy(xpath = "//input[@placeholder='Passenger Name' or @placeholder='Full Name']")
    public WebElementFacade passengerNameInput;

    @FindBy(xpath = "//label[contains(text(), 'UPI')] | //span[contains(text(), 'UPI')] | //*[contains(@value, 'UPI')]")
    public WebElementFacade upiPaymentOption;
}