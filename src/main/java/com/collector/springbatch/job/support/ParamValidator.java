package com.collector.springbatch.job.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ParamValidator implements JobParametersValidator {

        @Override
        public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
            String groupId = jobParameters.getString("groupId");
            log.info("groupId :: {}",groupId);
            String sn = jobParameters.getString("sn");
            log.info("sn :: {}",sn);

            if (groupId == null || groupId.isEmpty()) {
                throw new JobParametersInvalidException("The groupId parameter is missing or empty");
            }

            if (sn == null || sn.isEmpty()) {
                throw new JobParametersInvalidException("The sn parameter is missing or empty");
            }
        }
    }

