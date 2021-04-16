package com.bridgelabz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeePayrollFileIOService {
    public static String PAYROLL_FILE = "EmployeePayroll.txt";

    public void writeDataToFile(List<EmployeePayrollData> employeePayrollList){
        StringBuffer empBuffer = new StringBuffer();
        employeePayrollList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });
        try {
            Files.write(Paths.get(PAYROLL_FILE), empBuffer.toString().getBytes());
        }catch (IOException e) { }
    }

    public static void printData() {
        try{
            Files.lines(new File(PAYROLL_FILE).toPath()).forEach(System.out::println);
        }catch (IOException e){ }
    }

    public static long countEntries(){
        long count = 0;
        try {
            count = Files.lines(new File(PAYROLL_FILE).toPath()).count();
        }catch (IOException e){ }
        return count;
    }
}

