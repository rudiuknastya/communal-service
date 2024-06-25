package com.example.chairman.service;

import java.time.LocalDateTime;

public interface ScheduleService {
    void scheduleJob(Long formId, LocalDateTime date);
    void updateSchedule(Long formId, LocalDateTime date);

}
