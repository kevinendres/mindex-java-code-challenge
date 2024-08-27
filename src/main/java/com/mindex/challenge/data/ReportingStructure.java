package com.mindex.challenge.data;

/*
  Class defining the number of reports for a given employee. The number of reports is calculated
  transitively. This object is not persisted and must be recalculated on each request.

  Assumptions:
  The "employee" property should be the name of the employee, not their id. This is derived from the README where
  the example is given "The numberOfReports for employee John Lennon..." combined with the explicit
  name of the property being "employee."

  numberOfReports will not exceed Integer.MAX_VALUE.
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
}
