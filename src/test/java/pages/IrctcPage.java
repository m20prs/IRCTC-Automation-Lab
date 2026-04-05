package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class IrctcPage {
    private final Page page;

    public IrctcPage(Page page) {
        this.page = page;
    }

    public Locator okButton() { return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")); }
    public Locator loginLink() { return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("LOGIN")); }
    public Locator usernameInput() { return page.locator("input[formcontrolname='userid']"); }
    public Locator passwordInput() { return page.locator("input[formcontrolname='password']"); }
    public Locator signInButton() { return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("SIGN IN")); }
    
    public Locator fromStation() { return page.locator("p-autocomplete[formcontrolname='fromStation'] input"); }
    public Locator toStation() { return page.locator("p-autocomplete[formcontrolname='toStation'] input"); }
    public Locator suggestionItem() { return page.locator("li[role='option']").first(); }
    
    public Locator dateInput() { return page.locator("p-calendar[formcontrolname='journeyDate'] input"); }
    public Locator quotaDropdown() { return page.locator("#journeyQuota"); }
    public Locator searchButton() { return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")); }
    
    public Locator firstTrain() { return page.locator(".train-info-main-block").first(); }
    public Locator bookNowButton() { return page.locator("button.btnDefault.train_Search").first(); }
}