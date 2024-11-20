package persistence;

import io.ebean.Finder;
import recommender.VacuumCleanerModel;

import java.util.stream.Collectors;
import java.util.List;

public class VacuumCleanerFinder extends Finder<String, VacuumCleanerEntity> {

    public VacuumCleanerFinder() {
        super(VacuumCleanerEntity.class);
    }

    public List<VacuumCleanerModel> findAll() {
        return query().findList().stream()
                .map(VacuumCleanerModel::from)
                .collect(Collectors.toList());
    }
}
