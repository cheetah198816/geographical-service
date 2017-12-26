package config;

import client.RestExcelBatchJobClient;
import client.impl.RestBatchJobClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chetan on 25.12.2017.
 */
@Configuration
public class ClientConfig {

    @Value("${client.excelbath.baseUrl}")
    private String baseUrl;

    @Bean
    public RestExcelBatchJobClient restExcelBatchJobClient() {
        final RestExcelBatchJobClient restExcelBatchJobClient = new RestBatchJobClientImpl(baseUrl);
        return restExcelBatchJobClient;
    }

}
