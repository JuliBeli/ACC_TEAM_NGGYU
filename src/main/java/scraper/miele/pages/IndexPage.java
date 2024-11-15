package scraper.miele.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndexPage {

    private static final String INDEX_PAGE = "https://www.miele.ca/en/";

    public void acceptWebsiteCookies(WebDriver driver, WebDriverWait webDriverWait) {
        driver.get(INDEX_PAGE);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-button-group")));
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }
}
