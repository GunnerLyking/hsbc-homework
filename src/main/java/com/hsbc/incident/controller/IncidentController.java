package com.hsbc.incident.controller;

import com.hsbc.incident.entity.Incident;
import com.hsbc.incident.entity.Page;
import com.hsbc.incident.entity.Query;
import com.hsbc.incident.entity.ResponseWrapper;
import com.hsbc.incident.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "*")
public class IncidentController {
    @Autowired
    private IncidentService incidentService;

    @PostMapping
    public ResponseWrapper<Incident> createIncident(@RequestBody Incident incident) {
        incidentService.add(incident);
        return ResponseWrapper.ok(incident);
    }

    @PutMapping
    public ResponseWrapper<String> updateIncident(@RequestBody Incident incident) {
        incidentService.modify(incident);
        return ResponseWrapper.ok("操作成功");
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<String> deleteIncident(@PathVariable int id) {
        incidentService.delete(id);
        return ResponseWrapper.ok("操作成功");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<Incident> getIncident(@PathVariable int id) {
        return ResponseWrapper.ok(incidentService.getById(id));
    }

    @PostMapping("/query")
    public ResponseWrapper<Page<Incident>> getAllIncidents(@RequestBody Query query) {
        return ResponseWrapper.ok(incidentService.query(query.getPage(), query.getSize()));
    }
}
