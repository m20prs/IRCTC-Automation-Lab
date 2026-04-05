package stepdefinitions;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.model.util.EnvironmentVariables;
import utils.EncryptionUtils;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * Shared Base Class for IRCTC Automation.
 * Contains shared variables, credential loading, and retry utilities.
 */
public class BaseStepDefinition extends PageObject {

    protected EnvironmentVariables environmentVariables;

    public static final String IRCTC_URL = "https://www.irctc.co.in/nget/train-search";
    public static final String CONF_USER = "user.data.irctc.username";
    public static final String CONF_PASS = "user.data.irctc.password";

    protected static String username;
    protected static String decryptedPassword;

    // Retry configuration for professional stability
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    /**
     * Loads credentials from local properties file or environment variables.
     */
    public void initializeCredentials() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("credentials.properties"));
            username = props.getProperty(CONF_USER);
            decryptedPassword = EncryptionUtils.decode(props.getProperty(CONF_PASS));
            System.out.println("[INFO] Credentials loaded for: " + username);
        } catch (Exception e) {
            username = environmentVariables.optionalProperty(CONF_USER).orElse("default_user");
            decryptedPassword = EncryptionUtils.decode(environmentVariables.optionalProperty(CONF_PASS).orElse(""));
            System.out.println("[WARN] Using environment variable fallback for credentials.");
        }
    }

    /**
     * Standard browser launch and window management.
     */
    public void launchIrctc() {
        String targetUrl = environmentVariables.optionalProperty("webdriver.base.url").orElse(IRCTC_URL);
        getDriver().manage().window().maximize();
        getDriver().get(targetUrl);
    }

    /**
     * MENTOR FIX: Re-inserted to resolve 'cannot find symbol' compilation error.
     * Provides a robust retry mechanism for flaky UI interactions.
     */
    protected void retryAction(String actionName, Runnable action) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                System.out.println("[ATTEMPT " + attempt + "/" + MAX_RETRIES + "] " + actionName);
                action.run();
                return;
            } catch (Exception e) {
                if (attempt == MAX_RETRIES) {
                    throw new RuntimeException("[RETRY_EXHAUSTED] " + actionName + " failed after " + MAX_RETRIES + " attempts: " + e.getMessage(), e);
                }
                System.out.println("[RETRY] " + actionName + " failed (attempt " + attempt + "): " + e.getMessage());
                waitABit(RETRY_DELAY_MS);
            }
        }
    }

    /**
     * Accesses custom wait time for manual CAPTCHA entry.
     */
    protected int getCaptchaWaitTime() {
        return Integer.parseInt(environmentVariables.optionalProperty("captcha.wait.seconds").orElse("15"));
    }
}