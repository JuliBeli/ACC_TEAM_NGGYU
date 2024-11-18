package recommender;

import constant.Brand;

public class InputValidator {

    public boolean isValidBrand(String brand) {
        return Brand.SUPPORTED_BRANDS.contains(brand.toLowerCase());
    }

}
