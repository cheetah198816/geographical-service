package config;

import batch.reader.CustomItemReader;
import listener.JobIdExecutionListener;
import model.GeographicalClassesEntity;
import model.JobEntity;
import model.SectionEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by chetan on 24.12.2017.
 */


@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private FileConfiguration fileConfiguration;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    @StepScope
    public ItemReader<SectionEntity> reader(@Value("#{jobParameters[fileName]}") String pathToFile, @Value("#{jobExecutionContext[jobId]}") Long jobId) {
        final ItemReader<SectionEntity> itemReader = new CustomItemReader(pathToFile, jobId, fileConfiguration.getBaseFilePath());
        return itemReader;
    }

    @Bean
    JobLauncher jobLauncher() {
        final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }

    @Bean
    JobExplorer jobExplorer() throws Exception {
        final JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(dataSource);
        return factory.getObject();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setAnnotatedClasses(SectionEntity.class, GeographicalClassesEntity.class, JobEntity.class);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        return properties;

    }

    @Bean
    public ItemWriter<SectionEntity> writer() {
        final HibernateItemWriter<SectionEntity> sectionDataHibernateItemWriter = new HibernateItemWriter<>();
        sectionDataHibernateItemWriter.setSessionFactory(sessionFactory().getObject());
        return sectionDataHibernateItemWriter;
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        final JobExecutionListener jobExecutionListener = new JobIdExecutionListener();
        return jobExecutionListener;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step1")
                .<SectionEntity, SectionEntity>chunk(10)
                .reader(reader(null, null))
                .writer(writer())
                .build();
    }
}