package scraper.dyson;

import scraper.VacuumCleaner;

public class DysonVacuumCleaner implements VacuumCleaner {

    private final String url;
    private final String displayName;
    private final String brand;
    private double weightInLbs;
    private final double price;
    private double powerInWatts;
    private boolean isWireless;

    public DysonVacuumCleaner(String url, String displayName, double price, double weightInLbs, boolean isWireless, double powerInWatts) {
        this.url = url;
        this.displayName = displayName;
        this.brand = "Dyson";
        this.price = price;
        this.weightInLbs = weightInLbs;
        this.isWireless = isWireless;
        this.powerInWatts = powerInWatts;
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public String getURL() {
        return url;
    }

    public double getWeightInLbs() {
        return weightInLbs;
    }

    public double getPowerInWatts() {
        return powerInWatts;
    }

    public boolean isWireless() {
        return isWireless;
    }

}
