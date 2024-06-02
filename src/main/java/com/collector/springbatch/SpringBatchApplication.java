package com.collector.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job collectorJob;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            for (String arg : args) {
                if (arg.startsWith("--")) {
                    String[] keyValue = arg.substring(2).split("=");
                    if (keyValue.length == 2) {
                        jobParametersBuilder.addString(keyValue[0], keyValue[1]);
                    }
                }
            }

            jobParametersBuilder.addDate("run.id", new Date());

            // Debugging: Print the parsed parameters to check if they are correctly parsed
            jobParametersBuilder.toJobParameters().getParameters().forEach((key, value) -> {
                System.out.println("Parameter: " + key + " = " + value.getValue());
            });

            jobLauncher.run(collectorJob, jobParametersBuilder.toJobParameters());
        };
    }
}
