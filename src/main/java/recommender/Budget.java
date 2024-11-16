package recommender;

public class Budget {
    private final Double minimalPrice;
    private final Double maximumPrice;

    public Budget(double minimalPrice, double maximumPrice) {
        this.minimalPrice = minimalPrice;
        this.maximumPrice = maximumPrice;
    }

    public Double getMinimalPrice() {
        return minimalPrice;
    }

    public Double getMaximumPrice() {
        return maximumPrice;
    }
}
