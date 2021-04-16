package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollDataBaseServiceTest {
    @Test
    public void given3EmployeesInDB_whenChecked_ShouldReturnEmployeeCount() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        List<EmployeePayrollData.EmployeeDataBase> employeeDBDataList = employeeDBService.retrieveDBData(EmployeePayrollDataBaseService.EmployeeDataBaseService.IO_Service.DB_IO);
        Assertions.assertEquals(3, employeeDBDataList.size());
    }

    @Test
    public void givenDBWith3Employees_whenUpdatingSalary_ShouldMatchWithGivenSalary() throws SQLException, ClassNotFoundException, InterruptedException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        String name = "Teresa";
        double salary = 2500000;
        EmployeePayrollData.EmployeeDataBase employeeSalary = employeeDBService.updateSalary(name,salary);
        Assertions.assertEquals(salary, employeeSalary.getSalary());
    }

    @Test
    public void givenDateRange_ShouldReturnEmployeesWhoJoinedInDateRange() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        String startDate = "2018-01-01";
        String endDate = "2020-01-01";
        List<EmployeeData> employeeDBDataList = employeeDBService.retrieveDataInDateRange(startDate, endDate);
        Assertions.assertEquals(2, employeeDBDataList.size());
    }

    @Test
    public void givenEmployeeDatabase_ShouldReturnSumOfSalaryForFemales() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        double femaleSalary = employeeDBService.getTotalSalaryForGender("F");
        Assertions.assertEquals(2500000, femaleSalary);
    }

    @Test
    public void givenEmployeeDatabase_ShouldReturnAvgOfSalaryForFemales() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        double femaleSalary = employeeDBService.getAvgSalaryForGender("F");
        Assertions.assertEquals(2500000, femaleSalary);
    }

    @Test
    public void givenEmployeeDatabase_ShouldReturnMinSalaryForFemales() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        double femaleSalary = employeeDBService.getMinSalaryForGender("F");
        Assertions.assertEquals(2500000, femaleSalary);
    }

    @Test
    public void givenEmployeeDatabase_ShouldReturnMaxSalaryForFemales() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        double femaleSalary = employeeDBService.getMaxSalaryForGender("F");
        Assertions.assertEquals(2500000, femaleSalary);
    }

    @Test
    public void givenEmployeeDatabase_ShouldReturnCountOfFemales() throws SQLException, ClassNotFoundException {
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        int femaleSalary = employeeDBService.getCountGender("F");
        Assertions.assertEquals(1, femaleSalary);
    }

    @Test
    public void givenDetails_whenAddedToDatabase_ShouldReflectInList() throws ClassNotFoundException, SQLException{
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        String name = "Akash";
        double salary = 3500000;
        String gender = "M";
        String start = "2012-12-12";
        String dept = "DevOps";
        List<EmployeePayrollData.EmployeeDataBase> employeeList = employeeDBService.addNewEmployee(name, salary, gender, start, dept);
        Assertions.assertEquals(name, employeeList.get(0).name);
    }

    @Test
    public void givenName_ShouldRetrieveDataForActiveEmployees() throws ClassNotFoundException, SQLException{
        EmployeePayrollDataBaseService.EmployeeDataBaseService employeeDBService = new EmployeePayrollDataBaseService.EmployeeDataBaseService();
        String name = "Akash";
        int success = employeeDBService.deleteEmployee(name);
        Assertions.assertEquals(15, success);
    }
}

