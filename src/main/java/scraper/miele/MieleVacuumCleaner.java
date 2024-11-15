package scraper.miele;

import scraper.VacuumCleaner;

public class MieleVacuumCleaner implements VacuumCleaner {

    private final String url;
    private final String displayName;
    private final String brand;
    private double weightInLbs;
    private final double price;
    private double powerInWatts;
    private boolean isWireless;

    public MieleVacuumCleaner(String url, String displayName, double price) {
        this.url = url;
        this.displayName = displayName;
        this.brand = "Miele";
        this.price = price;
    }

    public void updateTechnicalData(double weightInLbs, double powerInWatts) {
        this.weightInLbs = weightInLbs;
        this.powerInWatts = powerInWatts;
    }

    public void setIsWireless(boolean isWireless) {
        this.isWireless = isWireless;
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
