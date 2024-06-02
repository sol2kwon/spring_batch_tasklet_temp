package com.collector.springbatch.job.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobLoggerListener implements JobExecutionListener {

    private static String BEFORE_MESSAGE = "{} Job is Running";
    private static String AFTER_MESSAGE = "{} Job is Done. (Status: {})";
    private static String FAILED_MESSAGE = "{} Job is Failed. (Status: {})";

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
        //job 실행전에 db에서 필요한 내용 조회
        jobExecution.getExecutionContext().put("myData", "객체전달~");
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE,jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info(FAILED_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
        }
    }
}
