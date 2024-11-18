package recommender;

import java.util.Scanner;

import static constant.Brand.SUPPORTED_BRANDS_IN_STRING;

public class Questionnaire {

    private final Scanner scanner;
    private final InputValidator inputValidator;

    public Questionnaire() {
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator();
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
        return null;
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
