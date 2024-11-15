package scraper.miele;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import scraper.VacuumCleaner;
import scraper.VacuumCleanerScraper;
import scraper.miele.pages.AllVacuumCleanersPage;
import scraper.miele.pages.IndexPage;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class MieleVacuumCleanerScraper implements VacuumCleanerScraper {

    @Override
    public Set<VacuumCleaner> scrapeVacuumCleaners() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait waitFor10Seconds = new WebDriverWait(driver, Duration.ofSeconds(10));

        IndexPage indexPage = new IndexPage();
        indexPage.acceptWebsiteCookies(driver, waitFor10Seconds);

        AllVacuumCleanersPage allVacuumCleanersPage = new AllVacuumCleanersPage();
        List<MieleVacuumCleaner> vacuumCleaners = allVacuumCleanersPage.fetchDetails(driver, waitFor10Seconds);

        driver.close();

        return Set.copyOf(vacuumCleaners);
    }
}
