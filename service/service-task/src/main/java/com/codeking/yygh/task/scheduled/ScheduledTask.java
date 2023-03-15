package com.codeking.yygh.task.scheduled;

import com.codeking.yygh.common.constant.MqConst;
import com.codeking.yygh.common.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;

    /**
     * 每天8点执行 提醒就诊
     */
    //@Scheduled(cron = "0 0 1 * * ?")，在 cron.qqe2.com 可以查看可视化，这种很多，这个只能写6位数，默认当前年
    @Scheduled(cron = "0/30 * * * * ?")
    public void task1() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}
