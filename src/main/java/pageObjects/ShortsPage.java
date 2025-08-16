package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShortsPage extends Page{
    public ShortsPage(WebDriver driver,  WebDriverWait wait) {
        super(driver, wait);
    }

    By shortsPlayer = new By.ByClassName("player-container");

    public boolean isShortsPlayerDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(shortsPlayer));
        }catch (TimeoutException exception){
            return false;
        }

        return true;
    }
}
