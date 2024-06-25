package com.example.chairman.quartzConfig.util;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimerUtil {
    public static JobDetail buildJobDetail(Class jobClass, Long formId){
        return JobBuilder
                .newJob(jobClass)
                .withIdentity(formId.toString())
                .usingJobData("callbackData",formId)
                .build();
    }
    public static SimpleTrigger buildTrigger(LocalDateTime date, Long formId){
        Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(formId.toString())
                .startAt(out)
                .build();
    }
}
