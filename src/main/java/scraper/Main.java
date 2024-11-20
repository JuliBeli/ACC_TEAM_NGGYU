package scraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import scraper.dyson.DysonVacuumCleanerScraper;
import scraper.miele.MieleVacuumCleanerScraper;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        List<VacuumCleanerScraper> scrapers = List.of(
                new DysonVacuumCleanerScraper()
        );

        Set<VacuumCleaner> vacuumCleaners = scrapers.stream()
                .map(VacuumCleanerScraper::scrapeVacuumCleaners)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        System.out.println(
                new ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(vacuumCleaners)
        );
    }
}