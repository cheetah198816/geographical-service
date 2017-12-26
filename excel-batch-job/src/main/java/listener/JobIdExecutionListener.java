package listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * Created by chetan on 25.12.2017.
 */
public class JobIdExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        final long jobId = jobExecution.getJobId();
        jobExecution.getExecutionContext().put("jobId", jobId);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
