import model.GeographicalClassesEntity;
import model.JobEntity;
import model.SectionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chetan on 29.12.2017.
 */
public class TestHelper {

    private static Random random = new Random();

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
        jobEntity.setId(random.nextLong());
        jobEntity.setFileName("geodata.xls");
        sectionEntity.setJob(jobEntity);
        sectionEntities.add(sectionEntity);
        jobEntity.setSectionEntityList(sectionEntities);

        return jobEntity;
    }
}
