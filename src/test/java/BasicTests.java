import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pageObjects.MainPage;
import pageObjects.ShortsPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(BasicFixture.class)
class BasicTests extends BasicFixture {

    @Execution(ExecutionMode.CONCURRENT)
    @Test
    void shouldNavigateToSearchResultsWhenSearchMovieName() {
        //Arrange
        MainPage mainPage = new MainPage(driver, wait);

        //Act
        driver.get("https://www.youtube.com/");
        mainPage.searchMovie("Szachowy Mentor", testConfig.isMobile());

        //Assert
        if (testConfig.isMobile()) {
            Assertions.assertEquals("https://m.youtube.com/results?sp=mAEA&search_query=Szachowy+Mentor", driver.getCurrentUrl());
        }else {
            Assertions.assertEquals("https://www.youtube.com/results?search_query=Szachowy+Mentor", driver.getCurrentUrl());
        }
    }

    @Execution(ExecutionMode.CONCURRENT)
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

    @Execution(ExecutionMode.CONCURRENT)
    @Test
    void shouldFailToTakeScreenShot() {
        throw new RuntimeException("Fail Test");
    }
}