package com.yrris.dtp;

import com.yrris.dtp.sdk.domain.model.entity.ThreadPoolConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RTopic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private RTopic dynamicThreadPoolRedisTopic;

    // 测试 Redis订阅发布消息
    @Test
    public void test_dynamicThreadPoolRedisTopic() throws InterruptedException {
        // 1.构造一个线程池配置变更对象
        ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity("dynamic-thread-pool-test-app", "threadPoolExecutor01");
        threadPoolConfigEntity.setCorePoolSize(100);
        threadPoolConfigEntity.setMaximumPoolSize(100);
        // 2.向Redis Topic发布变更消息
        dynamicThreadPoolRedisTopic.publish(threadPoolConfigEntity);

        // 3.阻塞主线程，防止测试方法提前退出
//        new CountDownLatch(1).await();
    }

}