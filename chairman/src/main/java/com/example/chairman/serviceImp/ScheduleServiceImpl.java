package com.example.chairman.serviceImp;


import com.example.chairman.quartzConfig.jobs.CloseVotingJob;
import com.example.chairman.quartzConfig.util.TimerUtil;
import com.example.chairman.service.ScheduleService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final Scheduler scheduler;
    private final Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);
    public ScheduleServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    @PostConstruct
    private void startScheduler(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }
    @PreDestroy
    private void shutScheduler(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }
    @Override
    public void scheduleJob(Long formId, LocalDateTime date){
        JobDetail jobDetail = TimerUtil.buildJobDetail(CloseVotingJob.class, formId);
        SimpleTrigger simpleTrigger = TimerUtil.buildTrigger(date, formId);
        try {
            scheduler.scheduleJob(jobDetail, simpleTrigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void updateSchedule(Long formId, LocalDateTime date) {
        try {
            scheduler.deleteJob(JobKey.jobKey(formId.toString()));
            scheduleJob(formId, date);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }
}
