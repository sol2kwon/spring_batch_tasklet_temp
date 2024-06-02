package com.collector.springbatch.job;

import com.collector.springbatch.job.support.JobLoggerListener;
import com.collector.springbatch.job.support.ParamValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobConfig {


    /**
     * //                    MyData myData = (MyData) chunkContext.getStepContext()
     * //                            .getStepExecution()
     * //                            .getJobExecution()
     * //                            .getExecutionContext()
     * //                            .get("객체꺼내~");
     * //
     * //                    System.out.println("Fetched Data: " + myData);
     **/
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ParamValidator paramValidator;

    @Autowired
    private JobLoggerListener loggerListener;

    @Bean
    public Job collectorJob() {
        return jobBuilderFactory.get("collectorJob")
                .incrementer(new RunIdIncrementer())
                .validator(paramValidator)
                .listener(loggerListener)
                .start(collectorStep1())
//                .on("FAILED").to(conditionalFailStep)
//                .from(conditionalStartStep)
//                .on("COMPLETED").to(conditionalCompletedStep)
//                .from(conditionalStartStep)
//                .on("*").to(conditionalAllStep)
//                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step collectorStep1() {
        return stepBuilderFactory.get("collectorStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("collectorStep1 start");
                    //메모리..


                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step collectorStep2() {
        return stepBuilderFactory.get("collectorStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("collectorStep1 start");
                    //메모리..


                    //저장


                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}

