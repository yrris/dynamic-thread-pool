package com.yrris.dtp.sdk.trigger.job;

import com.alibaba.fastjson.JSON;
import com.yrris.dtp.sdk.domain.IDynamicThreadPoolService;
import com.yrris.dtp.sdk.domain.model.entity.ThreadPoolConfigEntity;
import com.yrris.dtp.sdk.registry.IRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * 线程池数据上报任务
 */
public class ThreadPoolDataReportJob {

    private final Logger logger = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    // 通过注入服务获取线程池配置数据
    private final IDynamicThreadPoolService dynamicThreadPoolService;

    // 通过注册器上报数据
    private final IRegistry registry;

    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void execReportThreadPoolList() {
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolConfigList();
        registry.reportThreadPool(threadPoolConfigEntities);
        logger.info("动态线程池，上报线程池信息：{}", JSON.toJSONString(threadPoolConfigEntities));

        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntities) {
            registry.reportThreadPoolConfigParameter(threadPoolConfigEntity);
            logger.info("动态线程池，上报线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));
        }
    }

}
