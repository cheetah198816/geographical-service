package services;

import model.JobEntity;
import model.SectionEntity;

import java.util.List;

/**
 * Created by chetan on 25.12.2017.
 */
public interface GeographicalProcess {

    /**
     * Finds a job Entity.
     *
     * @param jobId jobId.
     * @return job Entity if found or else exception.
     */
    JobEntity findById(Long jobId);

    /**
     * Finds the list of Section Entity based on name or code or both
     *
     * @param name  name
     * @param code  code
     * @param jobId job Id
     * @return list of section entities found.
     */
    List<SectionEntity> findByNameAndCode(String name, String code, Long jobId);
}