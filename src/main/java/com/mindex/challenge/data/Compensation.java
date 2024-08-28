package com.mindex.challenge.data;

import java.time.LocalDate;
import java.util.Objects;

/*
  Class defining the salary for a given employee and the date that salary took effect.

  Assumptions:
  employee field must be a valid, extant employeeId.

  Negative salaries are not allowed and should be changed to 0 (e.g. for volunteers).

  Dates in the past are acceptable (e.g. for retroactive raises).
 */
public class Compensation {
  private String employee;
  private int salary;
  private LocalDate effectiveDate;

  public Compensation() {
  }

  public String getEmployee() {
    return employee;
  }

  public void setEmployee(String employee) {
    this.employee = employee;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public LocalDate getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(LocalDate effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Compensation)) {
      return false;
    }
    Compensation that = (Compensation) o;
    return salary == that.salary && Objects.equals(employee, that.employee)
        && Objects.equals(effectiveDate, that.effectiveDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, salary, effectiveDate);
  }
}
