package com.example.chairman.quartzConfig.jobs;

import com.example.chairman.service.VotingService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class CloseVotingJob implements Job {
    private final VotingService votingService;

    public CloseVotingJob(VotingService votingService) {
        this.votingService = votingService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long formId = jobExecutionContext.getJobDetail().getJobDataMap().getLong("callbackData");
        votingService.closeVoting(formId);
    }
}
