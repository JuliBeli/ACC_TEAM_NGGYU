package scraper.miele.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import scraper.miele.MieleVacuumCleaner;
import scraper.miele.dto.VacuumCleanerTechnicalData;

import java.util.List;

public class VacuumCleanerDetailPage {

    private final String URL;

    public VacuumCleanerDetailPage(String URL) {
        this.URL = URL;
    }

    public void getDetailInfo(WebDriver driver, MieleVacuumCleaner vacuumCleaner) {
        driver.get(URL);
        Actions action = new Actions(driver);

        try {
            List<WebElement> optionalProducts = driver.findElements(By.xpath("//h2[contains(text(), 'Optional products and accessories')]"));
            WebElement downloadPart = driver.findElement(By.xpath("//h2[contains(text(), 'Downloads, CAD, & apps')]"));
            if (!optionalProducts.isEmpty()) {
                action.moveToElement(optionalProducts.get(0)).build().perform();
            } else {
                action.moveToElement(downloadPart).build().perform();
            }

            WebElement technicalDetails = driver.findElement(By.cssSelector("div[spy-section-name='Technical Details']"));
            technicalDetails.findElement(By.xpath("//button[contains(text(), 'View more specs')]")).click();
            technicalDetails.findElement(By.xpath("//button[contains(text(), 'Technical data')]")).click();

            Thread.sleep(500);

            VacuumCleanerTechnicalData technicalData = captureTechnicalInfo(technicalDetails);
            vacuumCleaner.updateTechnicalData(technicalData.getCleanerWeight(), technicalData.getPowerInWatts());
            System.out.println("Technical data was captured successfully!");

            action.scrollByAmount(0, -200).build().perform();
            technicalDetails.findElement(By.xpath("//button[contains(text(), 'Mobility')]")).click();
            vacuumCleaner.setIsWireless(false);
            System.out.println("Mobility data was captured successfully!");
        } catch (NoSuchElementException exception) {
            System.out.println("Technical details are not provided.");
            return;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    private VacuumCleanerTechnicalData captureTechnicalInfo(WebElement technicalDetails) {
        double weight;
        try {
            String weightText = captureTextOfSiblingSpanElementByText(technicalDetails, "Weight in lbs");
            if (weightText.contains(" ")) {
                weight = Double.parseDouble(
                        weightText.substring(0, weightText.indexOf(" "))
                );
            } else {
                weight = Double.parseDouble(weightText);
            }
        } catch (NoSuchElementException exception) {
            weight = -1.0;
        }

        int power;
        try {
            power = Integer.parseInt(captureTextOfSiblingSpanElementByText(technicalDetails, "Power"));
        } catch (NoSuchElementException exception) {
            power = -1;
        }

        return new VacuumCleanerTechnicalData(weight, power);
    }

    private String captureTextOfSiblingSpanElementByText(WebElement webElement, String textContained) {
        return webElement.findElement(By.xpath("//span[contains(text(), '" + textContained + "')]"))
                .findElement(By.xpath("following-sibling::*"))
                .getText();
    }
}
