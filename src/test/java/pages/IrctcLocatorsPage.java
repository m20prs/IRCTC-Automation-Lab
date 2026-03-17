package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;

public class IrctcLocatorsPage extends PageObject {

    // --- Login Section ---
    @FindBy(xpath = "//*[text()=' LOGIN / REGISTER '] ")
    public WebElementFacade loginButton;

    @FindBy(xpath = "//*[@placeholder='User Name']")
    public WebElementFacade usernameInput;

    @FindBy(xpath = "//*[@placeholder='Password']")
    public WebElementFacade passwordInput;

    @FindBy(xpath = "//*[text()='SIGN IN']")
    public WebElementFacade signInButton;

    @FindBy(xpath = "//*[text()=' MY ACCOUNT '] ")
    public WebElementFacade myAccountVerify;

    // --- Search Section ---
    @FindBy(xpath = "//*[@formcontrolname='origin']")
    public WebElementFacade fromStationInput;

    @FindBy(xpath = "//*[@formcontrolname='destination']")
    public WebElementFacade toStationInput;

    @FindBy(xpath = "//*[@formcontrolname='journeyDate']")
    public WebElementFacade datePickerInput;

    @FindBy(xpath = "//*[@formcontrolname='journeyQuota']")
    public WebElementFacade quotaDropdown;

    @FindBy(xpath = "//*[@formcontrolname='journeyClass']")
    public WebElementFacade classDropdown;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Search')]")
    public WebElementFacade searchButton;

    // --- Passenger & Payment Section ---
    @FindBy(xpath = "//*[@placeholder='Passenger Name']")
    public WebElementFacade passengerNameInput;

    @FindBy(xpath = "//*[text()='UPI']")
    public WebElementFacade upiPaymentOption;
}