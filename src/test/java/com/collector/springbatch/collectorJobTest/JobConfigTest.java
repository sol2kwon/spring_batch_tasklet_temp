package com.collector.springbatch.collectorJobTest;
import com.collector.springbatch.SpringBatchApplicationTest;
import com.collector.springbatch.job.support.JobLoggerListener;
import com.collector.springbatch.job.support.ParamValidator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {JobConfigTest.TestConfig.class, SpringBatchApplicationTest.class})
public class JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void success() throws Exception{
        //when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        //then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);

    }

    @Configuration
    static class TestConfig {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Autowired
        private ParamValidator paramValidator;

        @Autowired
        private JobLoggerListener loggerListener;

        @Bean
        public Job conditionalStepJob(Step conditionalStartStep, Step conditionalAllStep, Step conditionalFailStep, Step conditionalCompletedStep) {
            return jobBuilderFactory.get("conditionalStepJob")
                    .incrementer(new RunIdIncrementer())
                    .validator(paramValidator)
                    .listener(loggerListener)
                    .start(conditionalStartStep)
//                    .on("FAILED").to(conditionalFailStep)
//                    .from(conditionalStartStep)
//                    .on("COMPLETED").to(conditionalCompletedStep)
//                    .from(conditionalStartStep)
//                    .on("*").to(conditionalAllStep)
//                    .end()
                    .build();
        }

        @Bean
        public Step conditionalStartStep() {
            return stepBuilderFactory.get("conditionalStartStep")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("conditional Start Step");
//
//                        Long myDataId = (Long) chunkContext.getStepContext()
//                                .getStepExecution()
//                                .getJobExecution()
//                                .getExecutionContext()
//                                .get("myDataId");
//
//                        // DB에서 실제 데이터 조회
//                        MyData myData = myRepository().findById(myDataId).orElseThrow(() -> new Exception("Data not found"));
//
//                        System.out.println("Fetched Data: " + myData);

                        return RepeatStatus.FINISHED;
                    })
                    .build();
        }

        @Bean
        public Step conditionalAllStep() {
            return stepBuilderFactory.get("conditionalAllStep")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("conditional All Step");
                        return RepeatStatus.FINISHED;
                    })
                    .build();
        }

        @Bean
        public Step conditionalFailStep() {
            return stepBuilderFactory.get("conditionalFailStep")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("conditional Fail Step");
                        return RepeatStatus.FINISHED;
                    })
                    .build();
        }

        @Bean
        public Step conditionalCompletedStep() {
            return stepBuilderFactory.get("conditionalCompletedStep")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("conditional Completed Step");
                        return RepeatStatus.FINISHED;
                    })
                    .build();
        }

    }
}
