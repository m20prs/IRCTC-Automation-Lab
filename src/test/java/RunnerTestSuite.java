import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin  = {"pretty"},
        features = "src/test/resources/features",
        glue    = {"stepdefinitions", "steps"},   // ← removed non-existent "starter" package
        tags    = "@tatkal_booking"
)
public class RunnerTestSuite {}