package com.kevin.statistics.schedule;

import com.kevin.statistics.service.DailyService;
import com.kevin.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-14 13:58
 */
@Component
public class ScheduleTask {
    @Autowired
    private DailyService dailyService;

    // 在每天凌晨一点，执行方法，把前一天的数据查询进行添加
    @Scheduled(cron = "0 0 1 * * ?")
     public void task1() {
         dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
