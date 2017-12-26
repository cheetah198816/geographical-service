import client.RestExcelBatchJobClient;
import dto.request.RegisterJobRequest;
import dto.response.RegisterJobResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import services.GeographicalService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by chetan on 25.12.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GeographicalApplication.class)
public class GeographicalIntegrationTest {

    @MockBean
    private RestExcelBatchJobClient restBatchJobClient;

    @Autowired
    GeographicalService geographicalService;

    @Test
    public void registerJobTest() {
        when(restBatchJobClient.getJobId(any())).thenReturn(26l);
        RegisterJobRequest registerJobRequest = new RegisterJobRequest();
        registerJobRequest.setFileName("geodata.xls");
        RegisterJobResponse registerJobResponse = geographicalService.registerJob(registerJobRequest);

        assertThat(registerJobResponse.getJobId()).isEqualTo(26l);
    }
}
