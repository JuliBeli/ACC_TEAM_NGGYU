package persistence;

import io.ebean.Model;
import scraper.VacuumCleaner;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class VacuumCleanerEntity extends Model {

    @Id
    private final String id;

    private final String url;
    private final String displayName;
    private final String brand;
    private final double weightInLbs;
    private final double price;
    private final double powerInWatts;
    private final boolean wireless;

    private VacuumCleanerEntity(
            String url, String displayName, String brand, double weightInLbs,
            double price, double powerInWatts, boolean wireless) {
        this.id = UUID.randomUUID().toString();
        this.url = url;
        this.displayName = displayName;
        this.brand = brand;
        this.weightInLbs = weightInLbs;
        this.price = price;
        this.powerInWatts = powerInWatts;
        this.wireless = wireless;
    }

    public static VacuumCleanerEntity from(VacuumCleaner vacuumCleaner) {
        return new VacuumCleanerEntity(
                vacuumCleaner.getURL(),
                vacuumCleaner.getDisplayName(),
                vacuumCleaner.getBrand(),
                vacuumCleaner.getWeightInLbs(),
                vacuumCleaner.getPrice(),
                vacuumCleaner.getPowerInWatts(),
                vacuumCleaner.isWireless()
        );
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBrand() {
        return brand;
    }

    public double getWeightInLbs() {
        return weightInLbs;
    }

    public double getPrice() {
        return price;
    }

    public double getPowerInWatts() {
        return powerInWatts;
    }

    public boolean isWireless() {
        return wireless;
    }
}
