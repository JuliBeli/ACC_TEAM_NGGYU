package persistence;

import io.ebean.Finder;

import java.util.List;

public class VacuumCleanerFinder extends Finder<String, VacuumCleanerEntity> {

    public VacuumCleanerFinder() {
        super(VacuumCleanerEntity.class);
    }

    public List<VacuumCleanerEntity> findAll() {
        return query().findList();
    }
}
