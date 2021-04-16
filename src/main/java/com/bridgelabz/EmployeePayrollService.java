package com.bridgelabz;

import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class EmployeePayrollService {
    public enum IOService{
        FILE_IO, CONSOLE_IO;
    }

    private List<EmployeePayrollData> employeePayrollList;
    private Object writeEmployeePayrollData;

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        super();
        this.employeePayrollList = employeePayrollList;
    }

    public EmployeePayrollService(){ }

    public static void main(String[] args) {

        System.out.println("Welcome to Employee Payroll Program!!");
        Scanner consoleInputReader = new Scanner(System.in);

        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);

        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData();

    }

    public void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employee ID ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name ");
        consoleInputReader.nextLine();
        String name = consoleInputReader.nextLine();
        System.out.println("Enter Employee Salary ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    public void writeEmployeePayrollDataTOFile(IOService ioService){
        if(ioService.equals(IOService.CONSOLE_IO)){
            System.out.println("\nWriting Employee Payroll Data to Console\n" + employeePayrollList);
        }else if(ioService.equals(IOService.FILE_IO)){
            new EmployeePayrollFileIOService().writeDataToFile(employeePayrollList);
        }
    }

    public void writeEmployeePayrollData() {
        System.out.println("Employee Payroll List: " + employeePayrollList);
    }

    public long readEmployeePayrollDataFromFile(IOService ioService){
        List<EmployeePayrollData> employeeList = new ArrayList<>();
        try{
            Files.lines(new File(String.valueOf(ioService)).toPath()).map(line -> line.trim()).
                    forEach(line -> System.out.println(line));
        }catch (IOException e){ }
        return EmployeePayrollFileIOService.countEntries();
    }
}

