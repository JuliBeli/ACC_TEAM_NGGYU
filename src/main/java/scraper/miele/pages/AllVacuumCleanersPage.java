package scraper.miele.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import scraper.miele.MieleVacuumCleaner;

import java.util.ArrayList;
import java.util.List;

public class AllVacuumCleanersPage {

    private static final String ALL_VACUUM_CLEANERS_PAGE = "https://www.miele.ca/en/Shop/products/all-vacuum-cleaners-14-c";

    public List<MieleVacuumCleaner> fetchDetails(WebDriver driver, WebDriverWait webDriverWait) {
        driver.get(ALL_VACUUM_CLEANERS_PAGE);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("app-product-card")));
        List<WebElement> products = driver.findElements(By.tagName("app-product-card"));

        List<MieleVacuumCleaner> vacuumCleaners = new ArrayList<>();
        for (WebElement ele : products) {
            String productCode = ele.findElement(By.className("product-code")).getText();
            String productName = ele.findElement(By.className("product-name")).getText();
            String color = ele.findElement(By.className("color")).getText();
            String price = ele.findElement(By.className("mb-2")).getText();
            price = price.substring(price.indexOf("$") + 1).replace(",", "");
            String detailPageUrl = ele.findElement(By.className("btn-outline-darkgray")).getAttribute("href");
            String displayName = String.format("%s - %s - %s", productCode, productName, color);

            vacuumCleaners.add(
                    new MieleVacuumCleaner(detailPageUrl, displayName, Double.parseDouble(price))
            );
        }

        for (MieleVacuumCleaner vacuumCleaner : vacuumCleaners) {
            VacuumCleanerDetailPage vacuumCleanerDetailPage = new VacuumCleanerDetailPage(vacuumCleaner.getURL());
            vacuumCleanerDetailPage.getDetailInfo(driver, vacuumCleaner);
        }

        return vacuumCleaners;
    }
}


