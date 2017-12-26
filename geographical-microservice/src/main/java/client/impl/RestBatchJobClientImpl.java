package client.impl;

import client.RestExcelBatchJobClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Created by chetan on 25.12.2017.
 */
public class RestBatchJobClientImpl implements RestExcelBatchJobClient {

    private String baseUrl;

    private Client client = ClientBuilder.newClient();

    public RestBatchJobClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Long getJobId(String fileName) {
        return client
                .target(baseUrl)
                .path("/runJob")
                .queryParam("fileName", fileName)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(Long.class);
    }

    @Override
    public String getJobStatus(Long jobId) {
        return client
                .target(baseUrl)
                .path("/status")
                .queryParam("jobId", jobId)
                .request()
                .get(String.class);
    }
}
