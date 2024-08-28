package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplUnitTest {

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  @Mock
  private EmployeeRepository employeeRepository;

  @Before
  public void setup() {
    Employee employee1 = new Employee();
    employee1.setEmployeeId("1");
    Employee employee2 = new Employee();
    employee2.setEmployeeId("2");
    Employee employee3 = new Employee();
    employee3.setEmployeeId("3");
    Employee employee4 = new Employee();
    employee4.setEmployeeId("4");
    Employee employee5 = new Employee();
    employee5.setEmployeeId("5");
    Employee employee6 = new Employee();
    employee6.setEmployeeId("6");

    employee1.setDirectReports(Arrays.asList(employee2, employee3, employee4));
    employee4.setDirectReports(Collections.singletonList(employee5));
    employee5.setDirectReports(Collections.singletonList(employee6));

    when(employeeRepository.findByEmployeeId("1")).thenReturn(employee1);
    when(employeeRepository.findByEmployeeId("2")).thenReturn(employee2);
    when(employeeRepository.findByEmployeeId("3")).thenReturn(employee3);
    when(employeeRepository.findByEmployeeId("4")).thenReturn(employee4);
    when(employeeRepository.findByEmployeeId("5")).thenReturn(employee5);
    when(employeeRepository.findByEmployeeId("6")).thenReturn(employee6);
  }

  @Test
  public void testGenerateReportingStructureValid() {
    ReportingStructure actual = employeeService.generateReportingStructure("1");
    assertEquals(new ReportingStructure("1", 5), actual);

    actual = employeeService.generateReportingStructure("2");
    assertEquals(new ReportingStructure("2", 0), actual);

    actual = employeeService.generateReportingStructure("4");
    assertEquals(new ReportingStructure("4", 2), actual);

    actual = employeeService.generateReportingStructure("5");
    assertEquals(new ReportingStructure("5", 1), actual);
  }

  @Test
  public void testGenerateReportingStructureThrows() {
    assertThrows(RuntimeException.class, () -> employeeService.generateReportingStructure("7"));
  }
}
