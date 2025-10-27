# Dynamic thread pool

A Spring Boot starter for dynamic thread pool management with real-time monitoring and configuration updates via Redis.

## Features

- **Dynamic Configuration**: Adjust thread pool parameters at runtime without restarting applications
- **Real-time Monitoring**: Track thread pool metrics including active threads, queue size, and pool utilization
- **Redis-based Registry**: Centralized configuration management using Redis pub/sub
- **Web Dashboard**: Simple HTML interface for viewing and modifying thread pool configurations
- **Spring Boot Integration**: Seamless integration as a Spring Boot starter

## Architecture

The project consists of three modules:

- **dynamic-thread-pool-spring-boot-starter**: Core starter library for client applications
- **dynamic-thread-pool-admin**: Admin service providing REST APIs for management
- **dynamic-thread-pool-test**: Test application demonstrating usage

## Quick Start

### 1. Add Dependency

```xml

<dependency>
  <groupId>com.yrris</groupId>
  <artifactId>dynamic-thread-pool-spring-boot-starter</artifactId>
  <version>1.0</version>
</dependency>

```

### 2. Configure Application

```yaml
# application.yml
spring:
  application:
    name: your-app-name

dynamic:
  thread:
    pool:
      config:
        enabled: true
        host: 127.0.0.1
        port: 6379
```

### 3. Define Thread Pools

```java
@Configuration
public class ThreadPoolConfig {
    
    @Bean("myThreadPool")
    public ThreadPoolExecutor myThreadPool() {
        return new ThreadPoolExecutor(
            20,  // corePoolSize
            50,  // maximumPoolSize
            5000L, // keepAliveTime
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(5000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
```

### 4. Start Admin Service

```bash
cd dynamic-thread-pool-admin
mvn spring-boot:run
```

Access the dashboard at: `http://localhost:8089/front/index.html`

## API Endpoints

### Query Thread Pool List
```bash
GET http://localhost:8089/api/v1/dynamic/thread/pool/query_thread_pool_list
```

### Query Thread Pool Config
```bash
GET http://localhost:8089/api/v1/dynamic/thread/pool/query_thread_pool_config?appName=your-app&threadPoolName=myThreadPool
```

### Update Thread Pool Config
```bash
POST http://localhost:8089/api/v1/dynamic/thread/pool/update_thread_pool_config
Content-Type: application/json

{
  "appName": "your-app",
  "threadPoolName": "myThreadPool",
  "corePoolSize": 30,
  "maximumPoolSize": 60
}
```

## How It Works

1. **Registration**: Client applications register thread pools with Redis on startup
2. **Monitoring**: A scheduled job reports thread pool metrics every 20 seconds
3. **Updates**: Configuration changes are published via Redis topics
4. **Listener**: Client applications listen for updates and apply changes dynamically

## Requirements

- Java 8+
- Spring Boot 2.7.x
- Redis 3.0+
- Maven 3.x

## Building from Source

```bash
mvn clean install
```

## Configuration Properties

| Property | Description | Default |
|----------|-------------|---------|
| `dynamic.thread.pool.config.enabled` | Enable dynamic thread pool | `false` |
| `dynamic.thread.pool.config.host` | Redis host | `127.0.0.1` |
| `dynamic.thread.pool.config.port` | Redis port | `6379` |
| `dynamic.thread.pool.config.pool-size` | Redis connection pool size | `64` |

## Dashboard Features

- View all registered thread pools across applications
- Monitor real-time metrics (active threads, queue size, etc.)
- Adjust core and maximum pool sizes on the fly
- Auto-refresh capability for continuous monitoring

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

