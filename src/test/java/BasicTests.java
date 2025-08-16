import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageObjects.MainPage;
import pageObjects.ShortsPage;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BasicTests extends BasicFixture {

    @Test
    void foo() {
        //Arrange
        MainPage mainPage = new MainPage(driver, wait);

        //Act
        driver.get("https://www.youtube.com/");
        mainPage.searchMovie("Szachowy Mentor");

        //Assert
        Assertions.assertEquals("https://www.youtube.com/results?search_query=Szachowy+Mentor", driver.getCurrentUrl());
    }

    @Test
    void foo2() {
        //Arrange
        MainPage mainPage = new MainPage(driver, wait);

        //Act
        driver.get("https://www.youtube.com/");
        ShortsPage shortsPage = mainPage.clickShortsButton();

        //Assert
        assertThat(shortsPage.isShortsPlayerDisplayed()).isTrue();
        assertThat(driver.getCurrentUrl()).contains("https://www.youtube.com/shorts");
    }
}