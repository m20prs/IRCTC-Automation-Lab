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

/**
 * Owns the @Before lifecycle hook AND shared utilities.
 *
 * Cucumber's rule: a class that defines @Before/@After must NOT be extended
 * by any step-definition class. The fix: IrctcStepDefinitions no longer
 * extends this class. It accesses shared state via @Steps-injected
 * IrctcUserSteps, which *does* extend this class — and that is fine because
 * IrctcUserSteps is a Serenity Steps class, not a Cucumber glue class.
 */
public class BaseStepDefinition extends PageObject {

    @Managed
    protected WebDriver driver;

    protected EnvironmentVariables environmentVariables;

    public static final String IRCTC_URL = "https://www.irctc.co.in/nget/train-search";
    public static final String CONF_USER = "user.data.irctc.username";
    public static final String CONF_PASS = "user.data.irctc.password";

    // Public static so IrctcUserSteps (and any other Steps class) can read them
    public static String username;
    public static String decryptedPassword;

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Before
    public void setUp() {
        initializeCredentials();
        launchIrctc();
    }

    // ── Credential Loading ────────────────────────────────────────────────────

    public void initializeCredentials() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("credentials.properties"));
            username = props.getProperty(CONF_USER);
            String encoded = props.getProperty(CONF_PASS);
            decryptedPassword = EncryptionUtils.decode(encoded);
            System.out.println("[INFO] Credentials loaded for: " + username);
        } catch (Exception e) {
            username = environmentVariables.optionalProperty(CONF_USER).orElse("default_user");
            String encoded = environmentVariables.optionalProperty(CONF_PASS).orElse("");
            decryptedPassword = EncryptionUtils.decode(encoded);
            System.out.println("[WARN] credentials.properties not found – using environment variables.");
        }
    }

    // ── Browser Launch ────────────────────────────────────────────────────────

    public void launchIrctc() {
        String targetUrl = environmentVariables
                .optionalProperty("webdriver.base.url")
                .orElse(IRCTC_URL);
        getDriver().manage().window().maximize();
        getDriver().get(targetUrl);
    }

    // ── Retry Utilities ───────────────────────────────────────────────────────

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
                System.out.println("[RETRY] " + actionName + " – attempt " + attempt + ": " + e.getMessage());
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

    protected void retryAction(String actionName, Runnable action) {
        retryAction(actionName, () -> {
            action.run();
            return null;
        });
    }

    // ── Config Helpers ────────────────────────────────────────────────────────

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