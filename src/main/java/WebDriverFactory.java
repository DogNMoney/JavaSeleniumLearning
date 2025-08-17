import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class WebDriverFactory {

    static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public WebDriver createWebDriver() throws Exception {
        TestConfig testConfig = readTestConfig();
        WebDriver webDriver;

        switch (testConfig.getBrowser()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "D:\\Work\\repos\\SeleniumJava\\chromedriver-win64\\chromedriver.exe");
                ChromeOptions options = createChromeOptions(testConfig);
                webDriver = new ChromeDriver(options);
                break;
            default:
                throw new Exception("Browser " + testConfig.getBrowser() + " not suppoerted");
        }

        driver.set(webDriver);
        return getDriver();
    }

    public static void removeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    private ChromeOptions createChromeOptions(TestConfig testConfig) {
        ChromeOptions options = new ChromeOptions();
        if (testConfig.isHeadless()){
            options.addArguments("--headless");
        }
        if (testConfig.isFullScreen()){
            options.addArguments("--start-maximized");
        }

        return  options;
    }

    public TestConfig readTestConfig() throws IOException {
        File file = new File(Paths.get("").toAbsolutePath().toString() + "/src/test/resources/configuration.json");
        System.out.println("Reading test config file: " + file.getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, TestConfig.class);
    }
}
