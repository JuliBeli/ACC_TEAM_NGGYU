package recommender;

import persistence.VacuumCleanerFinder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        VacuumCleanerFinder vacuumCleanerFinder = new VacuumCleanerFinder();
        List<VacuumCleanerModel> vacuumCleaners = vacuumCleanerFinder.findAll()
                .stream()
                .map(VacuumCleanerModel::from)
                .toList();

        vacuumCleaners.forEach(System.out::println);
    }
}
