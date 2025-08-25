import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public WebDriver createWebDriver(TestConfig testConfig) throws Exception {
        WebDriver webDriver;

        switch (TestProperties.getProperty("browser")) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
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
        if (Boolean.parseBoolean(TestProperties.getProperty("headless"))){
            options.addArguments("--headless");
        }
        if (Boolean.parseBoolean(TestProperties.getProperty("fullScreen"))){
            options.addArguments("--start-maximized");
        }
        if (Boolean.parseBoolean(TestProperties.getProperty("mobile"))){
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone X");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }

        return  options;
    }
}
