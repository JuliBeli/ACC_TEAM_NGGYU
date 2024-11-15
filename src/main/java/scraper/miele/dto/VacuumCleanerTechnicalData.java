package scraper.miele.dto;

public class VacuumCleanerTechnicalData {
    private final double cleanerWeight;
    private final double powerInWatts;

    public VacuumCleanerTechnicalData(double cleanerWeight, int powerInWatts) {
        this.cleanerWeight = cleanerWeight;
        this.powerInWatts = powerInWatts;
    }

    public double getCleanerWeight() {
        return cleanerWeight;
    }

    public double getPowerInWatts() {
        return powerInWatts;
    }
}
