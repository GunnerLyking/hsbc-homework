package com.hsbc.incident.service;

import com.hsbc.incident.entity.Incident;
import com.hsbc.incident.entity.Page;

public interface IncidentService {
    void add(Incident incident);

    void delete(int id);

    void modify(Incident incident);

    Incident getById(int id);

    Page<Incident> query(int page, int size);
}
