package com.hsbc.incident.service.impl;

import com.hsbc.incident.constant.IncidentStatus;
import com.hsbc.incident.constant.IncidentType;
import com.hsbc.incident.dao.IncidentDao;
import com.hsbc.incident.entity.Incident;
import com.hsbc.incident.entity.Page;
import com.hsbc.incident.service.IncidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class IncidentServiceImpl implements IncidentService {
    @Autowired
    private IncidentDao dao;

    private final BlockingQueue<Incident> incidentQueue = new LinkedBlockingQueue<>(1000);

    @Override
    public void add(Incident incident) {
        Assert.isTrue(IncidentStatus.valid(incident.getStatus()), "不存在的事件状态");
        Assert.isTrue(IncidentType.valid(incident.getType()), "不存在的事件类型");
        try {
            incidentQueue.put(incident);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("队列添加事件时出错", e);
        }
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public void modify(Incident incident) {
        Assert.isTrue(IncidentStatus.valid(incident.getStatus()), "不存在的事件状态");
        Assert.isTrue(IncidentType.valid(incident.getType()), "不存在的事件类型");
        dao.modify(incident);
    }

    @Override
    public Incident getById(int id) {
        return dao.getById(id);
    }

    @Override
    public Page<Incident> query(int page, int size) {
        return dao.query(page, size);
    }

    @PostConstruct
    private void init() {
        Runnable runnable = () -> {
            while (true) {
                Incident incident = incidentQueue.poll();
                if (incident != null) {
                    dao.add(incident);
                } else {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        log.error("新增事件线程失败", e);
                    }
                }
            }
        };
        new Thread(runnable, "新增事件线程").start();
    }

}
