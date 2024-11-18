package recommender;

import constant.Brand;
import org.apache.commons.lang3.tuple.Pair;

public class InputValidator {

    InputConverter inputConverter = new InputConverter();

    public boolean isValidBrand(String brand) {
        return Brand.SUPPORTED_BRANDS.contains(brand.toLowerCase());
    }

    public boolean isValidBudgetFormat(String budget) {
        return budget.matches(constant.RegularExpression.BUDGET);
    }

    public boolean isValidBudgetRange(String budget) {
        Pair<Double, Double> priceRange = inputConverter.extractPrices(budget);
        return priceRange.getLeft() >= 0 && priceRange.getRight() > 0;
    }
}
