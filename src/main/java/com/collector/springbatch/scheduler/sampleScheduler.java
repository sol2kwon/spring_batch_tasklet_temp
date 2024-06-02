package com.collector.springbatch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

//@Component
public class sampleScheduler {

//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job collectorJob;
//
//    @Scheduled(cron = "0 */1 * * * *")
//    public void jobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException {
//
//        JobParameters jobParameters = new JobParameters(
//                Collections.singletonMap("requestParam",new JobParameter(System.currentTimeMillis()))
//        );
//        jobLauncher.run(collectorJob,jobParameters);
//
//    }
}
