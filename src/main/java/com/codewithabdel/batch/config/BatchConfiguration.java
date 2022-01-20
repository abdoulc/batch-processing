package com.codewithabdel.batch.config;

import com.codewithabdel.batch.ProductItemProcessor;
import com.codewithabdel.batch.domain.Product;
import com.codewithabdel.batch.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public FlatFileItemReader<Product> reader(){
        return new FlatFileItemReaderBuilder<Product>()
                .name("productItemReader")
                .resource(new ClassPathResource("products-data.csv"))
                .delimited()
                .names(new String []{"name", "qty", "price", "description"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Product>(){{
                    setTargetType(Product.class);
                }})
                .build();
    }

    @Bean
    public ProductItemProcessor processor(){
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer(DataSource ds){
        return new JdbcBatchItemWriterBuilder<Product>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO products (name, qty, price, description, total) VALUES(:name, :qty, :price, :description, :total)")
                .dataSource(ds)
                .build();
    }

    @Bean
    public Job importUserToDbJob(JobCompletionNotificationListener listener, Step step1){
        return jobBuilderFactory.get("importProductsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end().build();
    }

    @Bean
    public Step setp1(JdbcBatchItemWriter<Product> writer){
        return  stepBuilderFactory.get("step1")
                .<Product, Product> chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
