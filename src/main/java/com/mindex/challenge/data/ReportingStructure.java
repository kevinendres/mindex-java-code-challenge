package com.mindex.challenge.data;

import java.util.Objects;

/*
  Class defining the number of reports for a given employee. The number of reports is calculated
  transitively. Instances of this class are not persisted and must be recalculated on each request.

  Assumptions:
  The "employee" property should be the id of the employee

  numberOfReports will not exceed Integer.MAX_VALUE
 */
public class ReportingStructure {
  private final String employee;
  private final int numberOfReports;

  public ReportingStructure(String employee, int numberOfReports) {
    this.employee = employee;
    this.numberOfReports = numberOfReports;
  }

  public String getEmployee() {
    return employee;
  }

  public int getNumberOfReports() {
    return numberOfReports;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ReportingStructure)) {
      return false;
    }
    ReportingStructure that = (ReportingStructure) o;
    return numberOfReports == that.numberOfReports && Objects.equals(employee,
        that.employee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, numberOfReports);
  }
}
