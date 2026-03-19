package stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import utils.EncryptionUtils;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Callable;

public class BaseStepDefinition extends PageObject {

    @Managed
    protected WebDriver driver;

    protected EnvironmentVariables environmentVariables;

    public static final String IRCTC_URL = "https://www.irctc.co.in/nget/train-search";
    protected static final String CONF_USER = "user.data.irctc.username";
    protected static final String CONF_PASS = "user.data.irctc.password";

    protected static String username;
    protected static String decryptedPassword;

    // Retry configuration
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    /**
     * Called once before every Scenario.
     * Loads credentials and launches the IRCTC portal.
     */
    @Before
    public void setUp() {
        initializeCredentials();
        launchIrctc();
    }

    protected void initializeCredentials() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("credentials.properties"));
            username = props.getProperty(CONF_USER);
            String encoded = props.getProperty(CONF_PASS);
            decryptedPassword = EncryptionUtils.decode(encoded);
            System.out.println("[INFO] Successfully loaded credentials for: " + username);
        } catch (Exception e) {
            // Fallback to Serenity EnvironmentVariables
            username = environmentVariables.optionalProperty(CONF_USER).orElse("default_user");
            String encoded = environmentVariables.optionalProperty(CONF_PASS).orElse("");
            decryptedPassword = EncryptionUtils.decode(encoded);
            System.out.println("[WARN] credentials.properties not found – using environment variables.");
        }
    }

    protected void launchIrctc() {
        String targetUrl = environmentVariables.optionalProperty("webdriver.base.url").orElse(IRCTC_URL);
        getDriver().manage().window().maximize();
        getDriver().get(targetUrl);
    }

    /**
     * Retry mechanism for flaky element interactions (returns a value).
     */
    protected <T> T retryAction(String actionName, Callable<T> action) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                System.out.println("[ATTEMPT " + attempt + "/" + MAX_RETRIES + "] " + actionName);
                return action.call();
            } catch (Exception e) {
                if (attempt == MAX_RETRIES) {
                    System.out.println("[FAILED] " + actionName + " after " + MAX_RETRIES + " attempts");
                    throw new RuntimeException("[RETRY_EXHAUSTED] " + actionName, e);
                }
                System.out.println("[RETRY] " + actionName + " failed (attempt " + attempt + "): " + e.getMessage());
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry sleep interrupted", ie);
                }
            }
        }
        return null;
    }

    /** Retry mechanism for void actions. */
    protected void retryAction(String actionName, Runnable action) {
        retryAction(actionName, () -> {
            action.run();
            return null;
        });
    }

    protected int getCaptchaWaitTime() {
        return Integer.parseInt(environmentVariables
                .optionalProperty("captcha.wait.seconds")
                .orElse("15"));
    }

    protected long getElementTimeout() {
        return Long.parseLong(environmentVariables
                .optionalProperty("element.timeout")
                .orElse("30000"));
    }
}