import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.CookiesDisposalPage;

import java.time.Duration;

public class BasicFixture {

    static ChromeDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Work\\repos\\SeleniumJava\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DeclineCookies();
    }

    @AfterAll
    static void tearDown() {
        driver.close();
        driver.quit();
    }

    private static void DeclineCookies(){
        driver.get("https://www.youtube.com/");

        CookiesDisposalPage cookiesDisposalPage = new CookiesDisposalPage(driver, wait);
        cookiesDisposalPage.clickDeclineCookiesButton();
    }
}
