import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.CookiesDisposalPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;

public class BasicFixture implements TestWatcher {

    static WebDriver driver;
    static WebDriverWait wait;
    static TestConfig testConfig;

    @BeforeEach
    void setUp() throws Exception {
        testConfig = readTestConfig();
        driver = new WebDriverFactory().createWebDriver(testConfig);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        declineCookies();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("✅ Test passed: " + context.getDisplayName());

        WebDriverFactory.removeDriver();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause){
        System.out.println("❌ Test failed: " + context.getDisplayName());

        try {
            takeScreenshot(context.getDisplayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WebDriverFactory.removeDriver();
    }

    private static void declineCookies(){
        driver.get("https://www.youtube.com/");

        CookiesDisposalPage cookiesDisposalPage = new CookiesDisposalPage(driver, wait);
        cookiesDisposalPage.clickDeclineCookiesButton();
    }

    public TestConfig readTestConfig() throws IOException {
        File file = new File(Paths.get("").toAbsolutePath().toString() + "/src/test/resources/configuration.json");
        System.out.println("Reading test config file: " + file.getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, TestConfig.class);
    }

    private static void takeScreenshot(String screenShotName) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenShotFile = screenshot.getScreenshotAs(OutputType.FILE);

        String foo = Paths.get("").toAbsolutePath().toString() + "/Screenshots/" + screenShotName + ".jpg";
        
        FileUtils.copyFile(screenShotFile, new File(Paths.get("").toAbsolutePath().toString() + "/Screenshots/" + screenShotName + ".jpg"));
    }
}
