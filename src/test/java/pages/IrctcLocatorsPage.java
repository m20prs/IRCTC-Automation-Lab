package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;

public class IrctcLocatorsPage extends PageObject {

    @FindBy(xpath = "//button[text()='OK']")
    public WebElementFacade globalOkButton;

    @FindBy(xpath = "//a[contains(text(),'LOGIN')]")
    public WebElementFacade loginLink;

    @FindBy(xpath = "//input[@formcontrolname='userid']")
    public WebElementFacade usernameInput;

    @FindBy(xpath = "//input[@formcontrolname='password']")
    public WebElementFacade passwordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'SIGN IN')]")
    public WebElementFacade signInButton;

    @FindBy(xpath = "//input[@role='searchbox' and contains(@aria-controls, 'pr_id_1')]")
    public WebElementFacade fromStationInput;

    @FindBy(xpath = "//input[@role='searchbox' and contains(@aria-controls, 'pr_id_2')]")
    public WebElementFacade toStationInput;

    @FindBy(xpath = "//p-calendar[@formcontrolname='journeyDate']//input")
    public WebElementFacade datePickerInput;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Search')]")
    public WebElementFacade searchButton;
}