package recommender;

import org.apache.commons.lang3.tuple.Pair;

public class InputConverter {

    public Pair<Double, Double> extractPrices(String budget) {
        String[] prices = budget.split("-");

        Double minPrice = prices[0].isEmpty() ? 0 : Double.parseDouble(prices[0]);
        Double maxPrice = prices.length == 1 ? Double.MAX_VALUE : Double.parseDouble(prices[1]);

        return Pair.of(minPrice, maxPrice);
    }

}
