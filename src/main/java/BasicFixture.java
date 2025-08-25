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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BasicFixture implements TestWatcher {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected TestConfig testConfig;

    @BeforeEach
    void setUp() throws Exception {
        this.testConfig = readTestConfig();
        this.driver = new WebDriverFactory().createWebDriver(testConfig);

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        declineCookies();
    }

    @AfterAll
    static void tearDown() throws IOException {
        TestReportManager testReportManager = TestReportManager.getInstance();
        TestReportManager.ReportSummary summary = new TestReportManager.ReportSummary(
                testReportManager.testsPassedCount.get(),
                testReportManager.testsFailedCount.get(),
                testReportManager.failedTests
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(Paths.get("").toAbsolutePath() + "/TestReports/TestReport" + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".json"), summary);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("✅ Test passed: " + context.getDisplayName());

        TestReportManager.getInstance().testsPassedCount.incrementAndGet();

        WebDriverFactory.removeDriver();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause){
        String screenShotFilePath;
        TestReportManager testReportManager = TestReportManager.getInstance();
        System.out.println("❌ Test failed: " + context.getDisplayName());

        testReportManager.testsFailedCount.incrementAndGet();

        try {
            screenShotFilePath = takeScreenshot(context.getDisplayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestReportManager.getInstance().failedTests.add(new TestReportManager.FailedTest(context.getDisplayName(), screenShotFilePath));
        WebDriverFactory.removeDriver();
    }

    private void declineCookies(){
        this.driver.get("https://www.youtube.com/");

        CookiesDisposalPage cookiesDisposalPage = new CookiesDisposalPage(driver, wait);
        cookiesDisposalPage.clickDeclineCookiesButton();
    }

    public TestConfig readTestConfig() throws IOException {
        File file = new File(Paths.get("").toAbsolutePath() + "/src/test/resources/configuration.json");
        System.out.println("Reading test config file: " + file.getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, TestConfig.class);
    }

    private String takeScreenshot(String screenShotName) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) this.driver;
        File screenShotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File screenShotFilePath = new File(Paths.get("").toAbsolutePath() + "/Screenshots/" + screenShotName + ".jpg");
        
        FileUtils.copyFile(screenShotFile, screenShotFilePath);

        return screenShotFilePath.getAbsolutePath();
    }
}
