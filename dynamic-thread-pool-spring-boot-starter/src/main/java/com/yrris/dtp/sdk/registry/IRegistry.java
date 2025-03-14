package com.yrris.dtp.sdk.registry;

import com.yrris.dtp.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * 注册中心接口
 */
public interface IRegistry {
    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolEntities);

    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);
}
