package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class AsyncTaskTest {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void AsyncTaskTest() throws InterruptedException, ExecutionException {

        for (int i = 0; i < 100; i++) {
            asyncTask.doTask1(i);
        }

        log.info("All tasks finished.");
    }
}