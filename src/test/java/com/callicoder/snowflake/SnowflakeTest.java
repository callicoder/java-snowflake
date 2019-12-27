package com.callicoder.snowflake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SnowflakeTest {

    @Test
    public void nextId_shouldGenerateIdWithCorrectBitsFilled() {
        Snowflake snowflake = new Snowflake(784);

        long beforeTimestamp = Instant.now().toEpochMilli();

        long id = snowflake.nextId();

        // Validate different parts of the Id
        long[] attrs = snowflake.parse(id);
        assertTrue(attrs[0] >= beforeTimestamp);
        assertEquals(784, attrs[1]);
        assertEquals(0, attrs[2]);
    }

    @Test
    public void nextId_shouldGenerateUniqueId() {
        Snowflake snowflake = new Snowflake(234);

        // Validate that the IDs are not same even if they are generated in the same ms
        long[] ids = new long[5000];
        for(int i = 0; i < 5000; i++) {
            ids[i] = snowflake.nextId();
        }

        for(int i = 0; i < ids.length; i++) {
            for(int j = i+1; j < ids.length; j++) {
                assertFalse(ids[i] == ids[j]);
            }
        }
    }

    @Test
    public void nextId_shouldGenerateUniqueIdIfCalledFromMultipleThreads() throws InterruptedException, ExecutionException  {
        ExecutorService executorService = Executors.newFixedThreadPool(50);

        Snowflake snowflake = new Snowflake(234);

        // Validate that the IDs are not same even if they are generated in the same ms in different threads
        Future<Long>[] futures = new Future[5000];
        for(int i = 0; i < 5000; i++) {
            futures[i] =  executorService.submit(() -> snowflake.nextId());
        }

        for(int i = 0; i < futures.length; i++) {
            for(int j = i+1; j < futures.length; j++) {
                assertFalse(futures[i].get() == futures[j].get());
            }
        }
    }
}