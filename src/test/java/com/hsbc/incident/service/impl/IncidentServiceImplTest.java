package com.hsbc.incident.service.impl;

import com.hsbc.incident.entity.Incident;
import com.hsbc.incident.entity.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IncidentServiceImplTest {

    @Autowired
    private IncidentServiceImpl incidentService;

    private ExecutorService executorService;

    @BeforeEach
    public void setup() {
        // 初始化线程池
        executorService = Executors.newFixedThreadPool(10);
    }

    @Test
    public void testAddIncidentUnderPressure() throws InterruptedException, ExecutionException {
        int totalRequests = 1000; // 模拟1000个并发请求
        Future<?>[] futures = new Future[totalRequests];

        for (int i = 0; i < totalRequests; i++) {
            final int incidentId = i;
            Callable<Void> task = () -> {
                Incident incident = new Incident();
                incident.setType("Error");
                incident.setStatus("Pending");
                incident.setMsg("Test Incident " + incidentId);
                incidentService.add(incident);
                return null;
            };
            futures[i] = executorService.submit(task);
        }

        for (Future<?> future : futures) {
            future.get();
        }

        Thread.sleep(10000);

        int size = 10;
        Page<Incident> incidentPage = incidentService.query(totalRequests / size, size);
        List<Incident> res = incidentPage.getContent();

        assertEquals(size, res.size(), "异步队列没有丢失数据");
    }
}
