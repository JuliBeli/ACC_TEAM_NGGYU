package recommender;

import persistence.VacuumCleanerFinder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<VacuumCleanerModel> vacuumCleaners = new VacuumCleanerFinder().findAll();

        Requirement requirement = new Questionnaire().gatherUserRequirements();
        List<VacuumCleanerModel> recommendedVacuumCleaners = new RecommendationProcessor().process(requirement, vacuumCleaners);

    }
}
