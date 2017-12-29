
import api.JobRestController;
import model.JobEntity;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by chetan on 29.12.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExcelBatchJobApplication.class)
public class BatchIntegrationTest {

    @Autowired
    JobRestController jobRestController;

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void testJob() {
        Long jobId = jobRestController.runJob("geodata.xls");
        assertThat(jobId).isNotNull();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JobEntity jobEntity = new JobEntity();
        session.load(jobEntity, jobId);
        assertThat(jobEntity.getFileName()).isNotEmpty();
        assertThat(jobEntity.getSectionEntityList().size()).isEqualTo(3);
        session.getTransaction().commit();
        session.close();
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testJobWrongFileName() {
        Long jobId = jobRestController.runJob("geodata1.xls");
        assertThat(jobId).isNotNull();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JobEntity jobEntity = new JobEntity();
        session.load(jobEntity, jobId);
        assertThat(jobEntity.getFileName()).isNotEmpty();
        assertThat(jobEntity.getSectionEntityList().size()).isEqualTo(3);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testJobStatus() {
        Long jobId = jobRestController.runJob("geodata.xls");
        String status = jobRestController.getStatus(jobId);

        assertThat(status).isNotEmpty();
    }
}
