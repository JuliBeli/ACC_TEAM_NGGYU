package scraper;

public interface VacuumCleaner {

    String getURL();

    String getDisplayName();

    String getBrand();

    double getWeightInLbs();

    double getPrice();

    double getPowerInWatts();

    boolean isWireless();

}
