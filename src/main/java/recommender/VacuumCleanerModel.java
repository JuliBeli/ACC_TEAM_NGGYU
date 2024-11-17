package recommender;

import persistence.VacuumCleanerEntity;

public class VacuumCleanerModel {
    private final String url;
    private final String displayName;
    private final String brand;
    private final double weightInLbs;
    private final double price;
    private final double powerInWatts;
    private final boolean isWireless;

    private VacuumCleanerModel(
            String url, String displayName, String brand, double weightInLbs,
            double price, double powerInWatts, boolean isWireless) {
        this.url = url;
        this.displayName = displayName;
        this.brand = brand;
        this.weightInLbs = weightInLbs;
        this.price = price;
        this.powerInWatts = powerInWatts;
        this.isWireless = isWireless;
    }

    public static VacuumCleanerModel from(VacuumCleanerEntity vacuumCleanerEntity) {
        return new VacuumCleanerModel(
                vacuumCleanerEntity.getUrl(),
                vacuumCleanerEntity.getDisplayName(),
                vacuumCleanerEntity.getBrand(),
                vacuumCleanerEntity.getWeightInLbs(),
                vacuumCleanerEntity.getPrice(),
                vacuumCleanerEntity.getPowerInWatts(),
                vacuumCleanerEntity.isWireless()
        );
    }
}
