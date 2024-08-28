package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    /*
        Generate the reporting structure (total number of reports) for a given employee.
     */
    @Override
    public ReportingStructure generateReportingStructure(String id) {
        LOG.debug("Generating reporting structure for employee [{}]", id);

        Employee employee = read(id);
        int numberOfReports = calculateNumReports(employee);
        return new ReportingStructure(id, numberOfReports);
    }

    /*
        Calculate the transitive total number of reports for a given employee using DFS.
     */
    private int calculateNumReports(Employee employee) {
        int numberOfReports = 0;
        // Use DFS to traverse the reporting tree
        Deque<Employee> employeeStack = new ArrayDeque<>();
        employeeStack.push(employee);
        while (!employeeStack.isEmpty()) {
            Employee currentEmployee = employeeStack.pop();
            List<Employee> directReports = currentEmployee.getDirectReports();
            if (directReports != null) {
                numberOfReports += directReports.size();
                for (Employee reportStub : directReports) {
                    // Employee objects in the directReports list are stubs, i.e. they only contain
                    // the employeeId of the reports, not the full Employee record needed to check
                    // their directReports field. Fetch full record for DFS
                    Employee report = read(reportStub.getEmployeeId());
                    employeeStack.push(report);
                }
            }
        }
        return numberOfReports;
    }
}
