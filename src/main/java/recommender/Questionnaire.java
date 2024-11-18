package recommender;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Scanner;

import static constant.Brand.SUPPORTED_BRANDS_IN_STRING;

public class Questionnaire {

    private final Scanner scanner;
    private final InputValidator inputValidator;
    private final InputConverter inputConverter;

    public Questionnaire() {
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator();
        this.inputConverter = new InputConverter();
    }

    public Requirement gatherUserRequirements() {
        String brand = askBrandPreference();
        Budget budget = askBudget();
        Boolean isPortable = askPortablePreference();
        Boolean isEcoFriendly = askEnergySavingPreference();
        Boolean isWireless = askWirelessPreference();
        return new Requirement(brand, budget, isPortable, isEcoFriendly, isWireless);
    }

    private String askBrandPreference() {
        System.out.println("Do you have a preferred brand? (y/N)");
        String userResponse = scanner.nextLine();

        if (userResponse.isBlank() || userResponse.equalsIgnoreCase("n")) {
            return null;
        } else if (userResponse.equalsIgnoreCase("y")) {
            System.out.println("Please enter the brand name:");
            System.out.println("(We provide: " + SUPPORTED_BRANDS_IN_STRING + ")");
            String brand = scanner.nextLine();
            if (inputValidator.isValidBrand(brand)) {
                return brand;
            } else {
                System.out.println("Sorry, we do not support this brand. Please choose another one.");
                return askBrandPreference();
            }
        } else {
            System.out.println("Invalid input. Please try again.");
            return askBrandPreference();
        }
    }

    private Budget askBudget() {
        System.out.println("Do you have a budget in mind? (y/N)");
        String userResponse = scanner.nextLine();

        if (userResponse.isBlank() || userResponse.equalsIgnoreCase("n")) {
            return new Budget(0, Double.MAX_VALUE);
        } else if (!userResponse.equalsIgnoreCase("y")) {
            System.out.println("Invalid input. Please try again.");
            return askBudget();
        }

        System.out.println("Please enter your budget:");
        String budget = scanner.nextLine();
        if (!inputValidator.isValidBudgetFormat(budget)) {
            System.out.println("Invalid budget format. Please try again.");
            return askBudget();
        }
        if (!inputValidator.isValidBudgetRange(budget)) {
            System.out.println("Invalid budget range. Please try again.");
            return askBudget();
        }

        Pair<Double, Double> priceRange = inputConverter.extractPrices(budget);
        return new Budget(priceRange.getLeft(), priceRange.getRight());
    }

    private Boolean askPortablePreference() {
        return null;
    }

    private Boolean askEnergySavingPreference() {
        return null;
    }

    private Boolean askWirelessPreference() {
        return null;
    }
}
