import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class WebDriverTabsHandler {

    public static void OpenNewTabAndSwitch(WebDriver driver, String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
    }
}
