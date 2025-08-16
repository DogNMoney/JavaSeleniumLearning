package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends Page {

    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    By searchBoxLocator = new By.ByClassName("yt-searchbox-input");
    By shortsButtonLocator = new By.ByXPath("//tp-yt-paper-item[contains(., 'Shorts')]");

    public MainPage searchMovie(String movieName) {
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(searchBoxLocator));

        searchInput.clear();
        searchInput.sendKeys(movieName);
        searchInput.sendKeys(Keys.ENTER);

        return this;
    }

    public ShortsPage clickShortsButton(){
        WebElement shortsButton = wait.until(ExpectedConditions.presenceOfElementLocated(shortsButtonLocator));

        shortsButton.click();

        return new ShortsPage(driver, wait);
    }
}
