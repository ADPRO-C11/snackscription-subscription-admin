package snackscription.subscriptionadmin.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class AsyncConfigTest {

    @Test
    void testAsyncExecutor() {

        AsyncConfiguration asyncConfiguration = new AsyncConfiguration();

        AsyncTaskExecutor executor = asyncConfiguration.getAsyncExecutor();

        assertNotNull(executor);

        assertEquals(5, ((ThreadPoolTaskExecutor) executor).getCorePoolSize());
        assertEquals(10, ((ThreadPoolTaskExecutor) executor).getMaxPoolSize());
        assertEquals(100, ((ThreadPoolTaskExecutor) executor).getQueueCapacity());
        assertEquals("Async-Executor-", ((ThreadPoolTaskExecutor) executor).getThreadNamePrefix());
    }
}