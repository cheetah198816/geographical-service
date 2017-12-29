import client.RestExcelBatchJobClient;
import com.thoughtworks.xstream.mapper.Mapper;
import dto.request.RegisterJobRequest;
import dto.response.GetResultsResponse;
import dto.response.RegisterJobResponse;
import dto.response.SearchResultsResponse;
import model.JobEntity;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    private  JobRepository jobRepository;

    @Before
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  void init() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
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
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        when(restBatchJobClient.getJobStatus(anyLong())).thenReturn("COMPLETED");

        GetResultsResponse getResultsResponse = geographicalService.getResults(26l);

        assertThat(getResultsResponse.getJobId()).isEqualTo(26l);
        assertThat(getResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsByCode() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC1", null, 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsByName() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults(null, "Geo Class 1", 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsByNameAndCode() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC1", "Geo Class 1", 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(1);
    }

    @Test
    public void testSearchResultsWhenNameAndCodeNotPassed() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults(null, null, 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

    @Test
    public void testSearchResultsWhenWrongCodePassed() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC2", null, 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

    @Test
    public void testSearchResultsWhenWrongNamePassed() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults(null, "XY", 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

    @Test
    public void testSearchResultsWhenWrongNameAndCodePassed() {
        JobEntity jobEntity = TestHelper.getJobEntityWithParsedResults();
        jobRepository.save(jobEntity);
        SearchResultsResponse searchResultsResponse = geographicalService.searchResults("GC2", "XY", 26l);

        assertThat(searchResultsResponse.getSectionDatas().size()).isEqualTo(0);
    }

}
