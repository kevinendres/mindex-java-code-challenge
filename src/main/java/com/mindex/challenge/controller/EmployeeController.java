package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final CompensationService compensationService;

    public EmployeeController(EmployeeService employeeService,
        CompensationService compensationService) {
        this.employeeService = employeeService;
        this.compensationService = compensationService;
    }

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    @GetMapping("/employee/{id}/reportingStructure")
    @ResponseStatus(HttpStatus.OK)
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        LOG.debug("Received employee reporting structure request for id [{}]", id);

        return employeeService.generateReportingStructure(id);
    }

    @GetMapping("/employee/{id}/compensation")
    @ResponseStatus(HttpStatus.OK)
    public Compensation getCompensation(@PathVariable String id) {
        LOG.debug("Received employee compensation get request for id [{}]", id);

        return compensationService.read(id);
    }

    @PostMapping("/employee/{id}/compensation")
    @ResponseStatus(HttpStatus.CREATED)
    public Compensation createCompensation(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received employee compensation create request for id [{}]", id);

        compensation.setEmployee(id);
        return compensationService.create(compensation);
    }
}
