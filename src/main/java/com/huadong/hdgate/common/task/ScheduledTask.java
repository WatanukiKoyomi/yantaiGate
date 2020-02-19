package com.huadong.hdgate.common.task;

import com.huadong.hdgate.common.utils.Lane1RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/19 16:19
 */
@Component
public class ScheduledTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    Lane1RedisUtils redisUtils;

    //@Scheduled(fixedDelayString = "${jobs.fixedDelay}")
    @Scheduled(fixedDelayString = "500")
    public void getTask1() {
        System.out.println("任务1,从配置文件加载任务信息，当前时间：" + dateFormat.format(new Date()));
        redisUtils.brpopQueue("test");
    }

}
