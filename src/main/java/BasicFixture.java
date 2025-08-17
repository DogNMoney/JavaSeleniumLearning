import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.CookiesDisposalPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;

public class BasicFixture implements TestWatcher {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setUp() throws Exception {
        driver = new WebDriverFactory().createWebDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        declineCookies();
    }

    @AfterAll
    static void tearDown() {
        driver.close();
        driver.quit();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("✅ Test passed: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause){
        System.out.println("❌ Test failed: " + context.getDisplayName());

        try {
            takeScreenshot(context.getDisplayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void declineCookies(){
        driver.get("https://www.youtube.com/");

        CookiesDisposalPage cookiesDisposalPage = new CookiesDisposalPage(driver, wait);
        cookiesDisposalPage.clickDeclineCookiesButton();
    }

    private static void takeScreenshot(String screenShotName) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenShotFile = screenshot.getScreenshotAs(OutputType.FILE);
        
        FileUtils.copyFile(screenShotFile, new File(Paths.get("").toAbsolutePath().toString() + "/Screenshots/" + screenShotName + ".jpg"));
    }
}
