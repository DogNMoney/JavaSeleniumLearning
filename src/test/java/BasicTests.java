import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pageObjects.MainPage;
import pageObjects.ShortsPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(BasicFixture.class)
class BasicTests extends BasicFixture {

    @Test
    void shouldNavigateToSearchResultsWhenSearchMovieName() {
        //Arrange
        MainPage mainPage = new MainPage(driver, wait);

        //Act
        driver.get("https://www.youtube.com/");
        mainPage.searchMovie("Szachowy Mentor");

        //Assert
        Assertions.assertEquals("https://www.youtube.com/results?search_query=Szachowy+Mentor", driver.getCurrentUrl());
    }

    @Test
    void shouldNavigateToShortsPageWhenClickShortsButton() {
        //Arrange
        MainPage mainPage = new MainPage(driver, wait);

        //Act
        driver.get("https://www.youtube.com/");
        ShortsPage shortsPage = mainPage.clickShortsButton();

        //Assert
        assertThat(shortsPage.isShortsPlayerDisplayed()).isTrue();
        assertThat(driver.getCurrentUrl()).contains("https://www.youtube.com/shorts");
    }

    @Test
    void shouldFailToTakeScreenShot() {
        throw new RuntimeException("Fail Test");
    }
}