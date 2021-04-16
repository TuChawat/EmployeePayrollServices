package com.bridgelabz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDataBaseService {
    public static class EmployeeDataBaseService {

        public enum IO_Service {DB_IO}

        public List<EmployeePayrollData.EmployeeDataBase> employeeDataBaseList = new ArrayList<EmployeePayrollData.EmployeeDataBase>();

        public List<EmployeePayrollData.EmployeeDataBase> retrieveDBData(IO_Service io_service) throws SQLException, ClassNotFoundException {
            if (io_service.equals(IO_Service.DB_IO)) {
                this.employeeDataBaseList = new EmployeePayrollDataBase().retrieveData();
            }
            return employeeDataBaseList;
        }

        public EmployeePayrollData.EmployeeDataBase updateSalary(String name, double salary) throws SQLException, ClassNotFoundException, InterruptedException {
            int result = new EmployeePayrollDataBase().updateSalary(name, salary);
            if (result == 1) {
                this.employeeDataBaseList = new EmployeePayrollDataBase().retrieveData();
                for (int i = 0; i < employeeDataBaseList.size(); i++) {
                    EmployeePayrollData.EmployeeDataBase emp = employeeDataBaseList.get(i);
                    if (emp.name.equals(name)) {
                        EmployeePayrollData.EmployeeDataBase employeeSalary = new EmployeePayrollData.EmployeeDataBase(emp.getEmpID(), emp.getName(), emp.getSalary(), emp.getGender(), emp.getStart());
                        return employeeSalary;
                    }
                }
            }
            return null;
        }

        public List<EmployeeData> retrieveDataInDateRange(String startDate, String endDate) throws SQLException, ClassNotFoundException {
            return new EmployeePayrollDataBase().retrieveInDateRange(startDate, endDate);
        }

        public double getTotalSalaryForGender(String gender) throws SQLException, ClassNotFoundException {
            return new EmployeePayrollDataBase().retrieveTotalSalaryBasedOnGender(gender);
        }

        public double getAvgSalaryForGender(String gender) throws SQLException, ClassNotFoundException {
            return new EmployeePayrollDataBase().retrieveAvgSalaryBasedOnGender(gender);
        }

        public double getMinSalaryForGender(String gender) throws SQLException, ClassNotFoundException {
            return new EmployeePayrollDataBase().retrieveMinSalaryBasedOnGender(gender);
        }

        public double getMaxSalaryForGender(String gender) throws SQLException, ClassNotFoundException {
            return new EmployeePayrollDataBase().retrieveMaxSalaryBasedOnGender(gender);
        }

        public int getCountGender(String gender) throws SQLException, ClassNotFoundException {
            return new EmployeePayrollDataBase().retrieveCountOfGender(gender);
        }

        public List<EmployeePayrollData.EmployeeDataBase> addNewEmployee(String name, double salary, String gender, String start, String dept) throws ClassNotFoundException, SQLException{
            EmployeePayrollData.EmployeeDataBase emp = null;
            int id = new EmployeePayrollDataBase().addNewEmployee(name, salary, gender, start, dept);
            ResultSet resultSet = new EmployeePayrollDataBase().retrieveSingleEmployee(id);
            while(resultSet.next()){
                if(resultSet.getInt("id") == id){
                    int empID = resultSet.getInt("id");
                    String empName = resultSet.getString("name");
                    double empSalary = resultSet.getDouble("salary");
                    String gen = resultSet.getString("gender");
                    LocalDate empDate = resultSet.getDate("start").toLocalDate();
                    emp = new EmployeePayrollData.EmployeeDataBase(empID, empName, empSalary, gen, empDate);
                }
            }
            this.employeeDataBaseList.add(emp);
            return employeeDataBaseList;
        }

        public int deleteEmployee(String name) throws ClassNotFoundException, SQLException{
            return new EmployeePayrollDataBase().deleteEmployee(name);
        }
    }
}
