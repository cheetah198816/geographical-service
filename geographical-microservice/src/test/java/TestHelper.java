import model.GeographicalClassesEntity;
import model.JobEntity;
import model.SectionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chetan on 29.12.2017.
 */
public class TestHelper {
    public static JobEntity getJobEntityWithParsedResults() {
        List<SectionEntity> sectionEntities = new ArrayList<>();
        List<GeographicalClassesEntity> geographicalClassesEntities = new ArrayList<>();
        GeographicalClassesEntity geographicalClassesEntity = new GeographicalClassesEntity();
        geographicalClassesEntity.setCode("GC1");
        geographicalClassesEntity.setName("Geo Class 1");

        SectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setSectionName("Section 1");
        geographicalClassesEntities.add(geographicalClassesEntity);
        geographicalClassesEntity.setSection(sectionEntity);
        sectionEntity.setGeographicalClassesEntityList(geographicalClassesEntities);

        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(26l);
        jobEntity.setFileName("geodata.xls");
        sectionEntity.setJob(jobEntity);
        sectionEntities.add(sectionEntity);
        jobEntity.setSectionEntityList(sectionEntities);

        return jobEntity;
    }
}
