package com.yrris.dtp.sdk.domain;

import com.yrris.dtp.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * 动态线程池服务
 */
public interface IDynamicThreadPoolService {
    List<ThreadPoolConfigEntity> queryThreadPoolConfigList();

    ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName);

    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);
}
