package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
  private String employeeUrl;
  private String compensationUrl;

  @Autowired
  private EmployeeService employeeService;

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void setup() {
    employeeUrl = "http://localhost:" + port + "/employee";
    compensationUrl = "http://localhost:" + port + "/employee/{id}/compensation";
  }


  @Test
  public void testCreateRead() {
    Employee testEmployee = new Employee();
    testEmployee.setFirstName("John");
    testEmployee.setLastName("Doe");
    testEmployee.setDepartment("Engineering");
    testEmployee.setPosition("Developer");

    Employee createdEmployee = restTemplate.postForEntity(
        employeeUrl,
        testEmployee,
        Employee.class
    ).getBody();

    assertNotNull(createdEmployee);

    Compensation testCompensation = new Compensation();
    testCompensation.setEmployee(createdEmployee.getEmployeeId());
    testCompensation.setSalary(90000);
    testCompensation.setEffectiveDate(LocalDate.of(2024, 8, 27));

    Compensation createdCompensation = restTemplate.postForEntity(
        compensationUrl,
        testCompensation,
        Compensation.class,
        createdEmployee.getEmployeeId()
    ).getBody();

    assertEquals(testCompensation, createdCompensation);

    Compensation readCompensation = restTemplate.getForEntity(
        compensationUrl,
        Compensation.class,
        createdCompensation.getEmployee()
    ).getBody();

    assertEquals(testCompensation, readCompensation);
  }

  }
