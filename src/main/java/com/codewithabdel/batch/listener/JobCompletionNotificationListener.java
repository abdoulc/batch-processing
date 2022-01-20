package com.codewithabdel.batch.listener;

import com.codewithabdel.batch.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void beforeJob(JobExecution jobExecution) {
        log.info("!!! IMPORT JOB started");
    }

    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("!!! JOB FINISHED!  results ==");
            jdbcTemplate.query("SELECT name, qty, price, description, total FROM products",
                    (rs,row)-> new Product(
                            rs.getString(1),
                            rs.getInt(2),
                            rs.getDouble(3),
                            rs.getString(4),
                            rs.getDouble(5)
                    ))
                    .forEach(person -> log.info( person + " Exists in the database"));
        }
    }


}
