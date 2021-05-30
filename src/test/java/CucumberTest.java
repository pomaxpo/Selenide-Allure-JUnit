import cucumber.api.CucumberOptions;
        import cucumber.api.junit.Cucumber;
        import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"steps", "hooks"},
        junit = "--step-notifications",
        features = "src/test/resources/features",
        plugin = "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"
        ,tags = {"@test"}
)
public class CucumberTest {
}