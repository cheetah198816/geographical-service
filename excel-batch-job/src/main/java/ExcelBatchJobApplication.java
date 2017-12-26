
import api.Resources;
import config.Configurations;
import javafx.application.Application;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by chetan on 22.12.2017.
 */
@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackageClasses = {Configurations.class, Resources.class})
public class ExcelBatchJobApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ExcelBatchJobApplication.class);
    }
}
