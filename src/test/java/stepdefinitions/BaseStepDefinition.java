package stepdefinitions;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import utils.EncryptionUtils;

public class BaseStepDefinition extends PageObject {

    @Managed
    protected WebDriver driver;

    protected EnvironmentVariables environmentVariables;

    public static final String IRCTC_URL = "https://www.irctc.co.in/nget/train-search";
    protected static final String CONF_USER = "user.data.irctc.username";
    protected static final String CONF_PASS = "user.data.irctc.password";

    protected String username;
    protected String decryptedPassword;

    protected void initializeCredentials() {
        this.username = environmentVariables.optionalProperty(CONF_USER).orElse("maddie_default");
        String encoded = environmentVariables.optionalProperty(CONF_PASS).orElse("");
        this.decryptedPassword = EncryptionUtils.decode(encoded);
    }
    
    protected void launchIrctc() {
        String targetUrl = environmentVariables.optionalProperty("webdriver.base.url").orElse(IRCTC_URL);
        
        // Force maximize via WebDriver API to override OS-level restrictions
        getDriver().manage().window().maximize();
        
        // Use getDriver() to ensure initialization and avoid NullPointer
        getDriver().get(targetUrl);
    }
}