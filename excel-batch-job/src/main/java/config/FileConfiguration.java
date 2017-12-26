package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chetan on 22.12.2017.
 */
@Configuration
public class FileConfiguration {

    @Value("${file.path}")
    private String baseFilePath;

    public String getBaseFilePath() {
        return baseFilePath;
    }
}
