package client;

/**
 * Created by chetan on 25.12.2017.
 */
public interface RestExcelBatchJobClient {

    /**
     * Fetches the jobId and starts processing the job on the batch.
     * @param fileName file Name of the file to be parsed.
     * @return job Id.
     */
    Long getJobId(String fileName);

    /**
     * Gets the status of the current job.
     * @param jobId job Id of the job.
     * @return Status of the job.
     */
    String getJobStatus(Long jobId);
}
