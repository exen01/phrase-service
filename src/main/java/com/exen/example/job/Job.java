package com.exen.example.job;

import com.exen.example.dao.common.CommonDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Job {

    private final CommonDao commonDao;

    private static int c = 0;

    //@Scheduled(cron = "*/3 * * * * *")
    @SchedulerLock(name = "job")
    public void job() throws InterruptedException {
        log.info("first_instance: {}", ++c);
        commonDao.testSchedulerLock("first_instance " + c);
        Thread.sleep(500);
    }
}
