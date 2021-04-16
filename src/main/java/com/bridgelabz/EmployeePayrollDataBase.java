package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDataBase {
    private Connection connection = new ConnectionFile().dbConnection();
    public EmployeePayrollDataBase() throws SQLException, ClassNotFoundException {
    }

    public List<EmployeePayrollData.EmployeeDataBase> retrieveData() throws SQLException {
        String query = "SELECT * FROM employee_payroll;";
        List<EmployeeData> employeeDBDataList = new ArrayList<>();
        Statement statement = connection.createStatement();
        try{
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate date = resultSet.getDate("start").toLocalDate();
                employeeDBDataList.add(new EmployeeData(id, name, salary, gender, date));
            }
        }catch (SQLException e){ }
        connection.close();
        return  employeeDBDataList;
    }


    public int updateSalary(String name, double salary) throws SQLException {
        String query = "UPDATE employee_payroll SET salary=? WHERE name=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, salary);
        preparedStatement.setString(2, name);
        int result = preparedStatement.executeUpdate();
        return result;
    }

    public List<EmployeeData> retrieveInDateRange(String startDate, String endDate) throws SQLException {
        String query = "SELECT * FROM employee_payroll WHERE start BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, startDate);
        preparedStatement.setString(2, endDate);
        List<EmployeeData> employeeDBDataList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            String gender = resultSet.getString("gender");
            LocalDate date = resultSet.getDate("start").toLocalDate();
            employeeDBDataList.add(new EmployeeData(id, name, salary, gender, date));
        }
        connection.close();
        return employeeDBDataList;
    }

    public double retrieveTotalSalaryBasedOnGender(String gender) throws SQLException {
        String query = "SELECT SUM(salary) FROM employee_payroll WHERE gender=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, gender);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return resultSet.getDouble("salary");
    }

    public double retrieveAvgSalaryBasedOnGender(String gender) throws SQLException {
        String query = "SELECT AVG(salary) FROM employee_payroll WHERE gender=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, gender);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return resultSet.getDouble("salary");
    }

    public double retrieveMinSalaryBasedOnGender(String gender) throws SQLException {
        String query = "SELECT MIN(salary) FROM employee_payroll WHERE gender=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, gender);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return resultSet.getDouble("salary");
    }

    public double retrieveMaxSalaryBasedOnGender(String gender) throws SQLException {
        String query = "SELECT MAX(salary) FROM employee_payroll WHERE gender=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, gender);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return resultSet.getDouble("salary");
    }

    public int retrieveCountOfGender(String gender) throws SQLException {
        String query = "SELECT COUNT(salary) FROM employee_payroll WHERE gender=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, gender);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return resultSet.getInt(1);
    }

    public int addNewEmployee(String name, double salary, String gender, String date, String dept) throws SQLException{
        String payroll = "insert into employee_payroll (name, salary, gender, start) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(payroll, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setDouble(2, salary);
        preparedStatement.setString(3, gender);
        preparedStatement.setDate(4, Date.valueOf(date));
        int success = preparedStatement.executeUpdate();
        int id = 0;
        if(success == 1){
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }else{
            System.out.println("0 Rows Affected");
            connection.close();
            System.exit(0);
        }
        String salaryQuery = "insert into salary (id, basic_pay, deductions, taxable_pay, tax, net_pay) values(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementSalary = connection.prepareStatement(salaryQuery);
        preparedStatementSalary.setInt(1, id);
        preparedStatementSalary.setDouble(2, salary);
        double deductions = salary * 0.2;
        preparedStatementSalary.setDouble(3, deductions);
        double taxable_pay = salary - deductions;
        preparedStatementSalary.setDouble(4, taxable_pay);
        double tax = taxable_pay * 0.1;
        preparedStatementSalary.setDouble(5, tax);
        double net_pay = salary - tax;
        preparedStatementSalary.setDouble(6, net_pay);
        int successSalary = preparedStatementSalary.executeUpdate();
        if(successSalary != 1){
            System.out.println("0 Rows Affected in Salary");
            System.exit(0);
        }
        String deptQuery = "insert into department (emp_id, dept) values(?, ?)";
        PreparedStatement preparedStatementDept = connection.prepareStatement(deptQuery);
        preparedStatementDept.setInt(1, id);
        preparedStatementDept.setString(2, dept);
        int deptSuccess = preparedStatementDept.executeUpdate();
        if(deptSuccess == 1){
            connection.close();
        }
        return id;
    }

    public ResultSet retrieveSingleEmployee(int id) throws SQLException{
        String query = "select * from employee_payroll where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public int deleteEmployee(String name) throws SQLException {
        String query = "update employee_payroll set is_active=false where name=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        int success = preparedStatement.executeUpdate();
        return success;
    }
}

