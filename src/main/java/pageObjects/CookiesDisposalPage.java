package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CookiesDisposalPage extends Page{

    public CookiesDisposalPage(WebDriver driver,  WebDriverWait wait) {
        super(driver, wait);
    }

    By declineCookiesButton = new By.ByXPath("//button[contains(., 'Odrzuć wszystko')]");

    public MainPage clickDeclineCookiesButton(){
        WebElement declineButton = wait.until(ExpectedConditions.presenceOfElementLocated(declineCookiesButton));

        declineButton.click();

        wait.until(ExpectedConditions.invisibilityOf(declineButton));

        return new MainPage(driver, wait);
    }
}
