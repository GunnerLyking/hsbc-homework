package com.hsbc.incident.dao;

import com.hsbc.incident.entity.Incident;
import com.hsbc.incident.entity.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IncidentDao {

    /**
     * 自增主键
     */
    private final AtomicInteger currentId = new AtomicInteger(1);

    /**
     * 模拟数据表,key作为主键
     */
    private final Map<Integer, Incident> table = new ConcurrentHashMap<>();

    public void add(Incident incident) {
        incident.setId(currentId.getAndIncrement());
        incident.setCreateTime(new Date());
        table.put(incident.getId(), incident);
    }

    public void delete(int id) {
        table.remove(id);
    }

    public void modify(Incident incident) {
        table.put(incident.getId(), incident);
    }

    public Incident getById(int id) {
        return table.get(id);
    }

    public Page<Incident> query(int page, int size) {
        List<Incident> incidents = new ArrayList<>(table.values());
        incidents.sort(Comparator.comparingInt(Incident::getId));
        int start = (page - 1) * size;
        int end = Math.min(incidents.size(), start + size);
        return new Page<>(incidents.subList(start, end), incidents.size(), page, size);
    }
}
