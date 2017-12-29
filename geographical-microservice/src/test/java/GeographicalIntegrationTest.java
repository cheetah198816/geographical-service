import client.RestExcelBatchJobClient;
import dto.request.RegisterJobRequest;
import dto.response.GetResultsResponse;
import dto.response.RegisterJobResponse;
import dto.response.SearchResultsResponse;
import model.JobEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import repositories.JobRepository;
import services.GeographicalService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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
    private GeographicalService geographicalService;

    @Autowired
    private JobRepository jobRepository;

    private Long jobId;

    @Before
    public void init() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        JobEntity savedJobEntity = jobRepository.save(jobEntity);
        jobId = savedJobEntity.getId();
    }

    @Test
    public void registerJobTest() {
        when(restBatchJobClient.getJobId(any())).thenReturn(26l);
        RegisterJobRequest registerJobRequest = new RegisterJobRequest();
        registerJobRequest.setFileName("geodata.xls");
        RegisterJobResponse registerJobResponse = geographicalService.registerJob(registerJobRequest);

        assertThat(registerJobResponse.getJobId()).isEqualTo(26l);
    }

    @Test
    public void testGetResults() {
        when(restBatchJobClient.getJobStatus(anyLong())).thenReturn("COMPLETED");

        GetResultsResponse getResultsResponse = geographicalService.getResults(jobId);

        assertThat(getResultsResponse.getJobId()).isEqualTo(jobId);
        assertThat(getResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsByCode() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC1", null, jobId);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsByName() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults(null, "Geo Class 1", jobId);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsByNameAndCode() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC1", "Geo Class 1", jobId);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsWhenNameAndCodeNotPassed() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults(null, null, jobId);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

    @Test
    public void testSearchResultsWhenWrongCodePassed() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC2", null, jobId);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

    @Test
    public void testSearchResultsWhenWrongNamePassed() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults(null, "XY", 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

    @Test
    public void testSearchResultsWhenWrongNameAndCodePassed() {
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC2", "XY", jobId);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

}
