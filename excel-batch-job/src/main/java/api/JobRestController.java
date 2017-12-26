package api;

import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chetan on 24.12.2017.
 */
@RestController
public class JobRestController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private JobExplorer jobExplorer;

    /**
     * Gets the status of the current Job.
     *
     * @param jobId jobId.
     * @return status string
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String getStatus(@RequestParam("jobId") Long jobId) {
        final JobExecution jobExecution = jobExplorer.getJobExecution(jobId);
        final BatchStatus batchStatus = jobExecution.getStatus();
        return batchStatus.name();
    }

    /**
     * runs the particular job
     *
     * @param fileName file Name
     * @return jobId.
     */
    @RequestMapping(value = "/runJob", method = RequestMethod.GET, produces = "application/json")
    public Long runJob(@RequestParam("fileName") String fileName) {
        try {
            final JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addString("fileName", fileName)
                            .addLong("time", System.currentTimeMillis()).toJobParameters();
            final JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            return jobExecution.getJobId();
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            //log the error.
        } catch (JobInstanceAlreadyCompleteException e) {
            //log the error.
        } catch (JobParametersInvalidException e) {
            //log the error.
        }
        return null;
    }
}
