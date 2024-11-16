package recommender;

public class Requirement {
    private String brand;

    private Budget budge;

    private Boolean isPortable;

    private Boolean isEcoFriendly;

    private Boolean isWireless;

    public Requirement() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Budget getBudge() {
        return budge;
    }

    public void setBudge(Budget budge) {
        this.budge = budge;
    }

    public Boolean getPortable() {
        return isPortable;
    }

    public void setPortable(Boolean portable) {
        isPortable = portable;
    }

    public Boolean getEcoFriendly() {
        return isEcoFriendly;
    }

    public void setEcoFriendly(Boolean ecoFriendly) {
        isEcoFriendly = ecoFriendly;
    }

    public Boolean getWireless() {
        return isWireless;
    }

    public void setWireless(Boolean wireless) {
        isWireless = wireless;
    }
}
